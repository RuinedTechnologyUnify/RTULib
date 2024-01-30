package com.github.ipecter.rtu.lib;

import com.github.ipecter.rtu.lib.commands.Command;
import com.github.ipecter.rtu.lib.listeners.MotdOnJoin;
import com.github.ipecter.rtu.lib.managers.ConfigManager;
import com.github.ipecter.rtu.lib.plugin.RTUPlugin;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class RTULib extends RTUPlugin {

    @Getter
    private static RTULib instance;

    @Getter
    private final ConfigManager configManager = new ConfigManager();

    @Getter
    private final Map<String, RTUPlugin> plugins = new HashMap<>();
    private final Map<String, Boolean> hooks = new HashMap<>();

    public RTULib() {
        super(MiniMessage.miniMessage().deserialize("<gradient:#6680ff:#cc66ff>【 RTULib 】</gradient>"));
    }

    public void loadPlugin(RTUPlugin plugin) {
        console(MiniMessage.miniMessage().deserialize("<white>loading RTUPlugin: " + plugin.getName() + "</white>"));
        plugins.put(plugin.getName(), plugin);
    }

    public void unloadPlugin(RTUPlugin plugin) {
        console(MiniMessage.miniMessage().deserialize("<white>unloading RTUPlugin: " + plugin.getName() + "</white>"));
        plugins.remove(plugin.getName());
    }

    public boolean isEnabledDependency(String dependencyName) {
        return hooks.getOrDefault(dependencyName, false);
    }

    public void hookDependency(String dependencyName) {
        hooks.put(dependencyName, Bukkit.getPluginManager().isPluginEnabled(dependencyName));
    }

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        registerEvent(new MotdOnJoin());
        registerCommand(new Command());
        configManager.init();
        console(MiniMessage.miniMessage().deserialize("<green>Enable!</green>"));
    }

    @Override
    public void disable() {
        console(MiniMessage.miniMessage().deserialize("<red>Disable!</red>"));
    }
}
