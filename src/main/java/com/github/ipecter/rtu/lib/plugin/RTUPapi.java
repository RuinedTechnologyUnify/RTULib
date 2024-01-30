package com.github.ipecter.rtu.lib.plugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public abstract class RTUPapi extends PlaceholderExpansion {

    private final RTUPlugin plugin;

    public RTUPapi(RTUPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    public @NotNull String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        return request(offlinePlayer, params.split("_"));
    }

    public abstract String request(OfflinePlayer offlinePlayer, String[] params);
}
