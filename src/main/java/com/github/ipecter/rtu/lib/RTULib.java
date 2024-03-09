package com.github.ipecter.rtu.lib;

import com.github.ipecter.rtu.lib.commands.Command;
import com.github.ipecter.rtu.lib.listeners.MotdOnJoin;
import com.github.ipecter.rtu.lib.managers.ConfigManager;
import com.github.ipecter.rtu.lib.nms.NMS;
import com.github.ipecter.rtu.lib.nms.v1_17_r1.NMS_1_17_R1;
import com.github.ipecter.rtu.lib.nms.v1_18_r1.NMS_1_18_R1;
import com.github.ipecter.rtu.lib.nms.v1_18_r2.NMS_1_18_R2;
import com.github.ipecter.rtu.lib.nms.v1_19_r1.NMS_1_19_R1;
import com.github.ipecter.rtu.lib.nms.v1_19_r2.NMS_1_19_R2;
import com.github.ipecter.rtu.lib.nms.v1_19_r3.NMS_1_19_R3;
import com.github.ipecter.rtu.lib.nms.v1_20_r1.NMS_1_20_R1;
import com.github.ipecter.rtu.lib.nms.v1_20_r2.NMS_1_20_R2;
import com.github.ipecter.rtu.lib.nms.v1_20_r3.NMS_1_20_R3;
import com.github.ipecter.rtu.lib.plugin.RTUPlugin;
import com.github.ipecter.rtu.lib.plugin.inventory.RTUInventoryListener;
import com.github.ipecter.rtu.lib.util.common.VersionUtil;
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
    private static NMS NMS;

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
        loadNMS();
    }

    private String nmsVersion;

    private void loadNMS() {
        nmsVersion = VersionUtil.getNMSVersion(VersionUtil.getVersionStr());
        switch (nmsVersion) {
            case "v1_17_R1" -> NMS = new NMS_1_17_R1();
            case "v1_18_R1" -> NMS = new NMS_1_18_R1();
            case "v1_18_R2" -> NMS = new NMS_1_18_R2();
            case "v1_19_R1" -> NMS = new NMS_1_19_R1();
            case "v1_19_R2" -> NMS = new NMS_1_19_R2();
            case "v1_19_R3" -> NMS = new NMS_1_19_R3();
            case "v1_20_R1" -> NMS = new NMS_1_20_R1();
            case "v1_20_R2" -> NMS = new NMS_1_20_R2();
            case "v1_20_R3" -> NMS = new NMS_1_20_R3();
            default -> {
                Bukkit.getLogger().warning("Server version is unsupported version, Disabling RTULib...");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    @Override
    public void enable() {
        console(MiniMessage.miniMessage().deserialize("<white>NMS: " + nmsVersion + "</white>"));
        registerEvent(new MotdOnJoin());
        registerEvent(new RTUInventoryListener());
        registerCommand(new Command());
        configManager.init();
        console(MiniMessage.miniMessage().deserialize("<green>Enable!</green>"));
    }

    @Override
    public void disable() {
        console(MiniMessage.miniMessage().deserialize("<red>Disable!</red>"));
    }
}
