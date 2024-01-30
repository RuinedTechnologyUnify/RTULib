package com.github.ipecter.rtu.lib.managers.data;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

public class MongoDB extends Data {

    private final Gson GSON = new Gson();
    @Getter
    private String name;

    public MongoDB(String uri, String databaseName, String collectionName) {
        //TODO: Connect and Init
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            try {
                //GSON.toJso
                // Inserts a sample document describing a movie into the collection
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("title", "Ski Bloopers")
                        .append("genres", Arrays.asList("Documentary", "Comedy")));
                // Prints the ID of the inserted document
                System.out.println("Success! Inserted document id: " + result.getInsertedId());

                // Prints a message if any exceptions occur during the operation
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    void set(String key, String value) {

    }
}
