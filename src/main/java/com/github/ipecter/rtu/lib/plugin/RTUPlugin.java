package com.github.ipecter.rtu.lib.plugin;

import com.github.ipecter.rtu.lib.RTULib;
import com.github.ipecter.rtu.lib.util.common.VersionUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class RTUPlugin extends JavaPlugin {

    @Getter
    private static RTUPlugin plugin;
    @Getter
    private final Component prefix;
    @Getter
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        if (VersionUtil.isSupportVersion("1.17.1")) {
            plugin = this;
            adventure = BukkitAudiences.create(this);
        } else {
            Bukkit.getLogger().warning("Server version is unsupported version (< 1.17.1), Disabling this plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        for (String plugin : this.getDescription().getSoftDepend()) RTULib.getInstance().hookDependency(plugin);
        enable();
        RTULib.getInstance().loadPlugin(this);
    }

    @Override
    public void onDisable() {
        disable();
        RTULib.getInstance().unloadPlugin(this);
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    @Override
    public void onLoad() {
        load();
    }

    public void console(Component message) {
        getAdventure().console().sendMessage(getPrefix().append(Component.text(" ")).append(message));
    }

    public void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(RTUCommand command) {
        Objects.requireNonNull(getCommand(command.getName())).setExecutor(command);
    }

    public void load() {
    }

    public void enable() {
    }

    public void disable() {
    }

}