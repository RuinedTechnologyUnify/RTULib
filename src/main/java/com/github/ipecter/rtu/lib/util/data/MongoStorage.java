package com.github.ipecter.rtu.lib.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Map;

public class MongoStorage implements Storage {

    private final Gson gson = new Gson();
    private final MongoClient client;
    private final MongoDatabase database;

    public MongoStorage(MongoInfo info) {
        // Replace the placeholder with your Atlas connection string
        String uri = "mongodb://" + info.getUsername() + ":" + info.getPassword() + "@" + info.getIp() + ":" + info.getPort();
        ;
        // Construct a ServerApi instance using the ServerApi.builder() method
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(info.getDatabase());
    }

    @Override
    public boolean add(String collection, JsonObject data) {
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Document setData = Document.parse(gson.toJson(data));
        mongoCollection.insertOne(setData);
        return true;
    }

    @Override
    public boolean set(String collection, Pair<String, Object> find, Pair<String, Object> data) {
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        Bson filter = Filters.eq(find.getKey(), find.getValue());
        if (data == null) {
            DeleteResult result = mongoCollection.deleteOne(filter);
            return result.wasAcknowledged();
        } else {
            UpdateOptions options = new UpdateOptions().upsert(true);
            Document setData = new Document(data.getKey(), data.getValue());
            Document update = new Document("$set", setData);
            mongoCollection.updateOne(filter, update, options);
            return true;
        }
    }


    @Override
    public JsonObject get(String collection, Pair<String, Object> find) {
        Bson filter = Filters.eq(find.getKey(), find.getValue());
        Document result = database.getCollection(collection).find(filter).first();
        return result != null ? JsonParser.parseString(result.toJson()).getAsJsonObject() : null;
    }


    @Override
    public void close() {
        client.close();
    }
}