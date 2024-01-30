package com.github.ipecter.rtu.lib.listeners;

import com.github.ipecter.rtu.lib.RTULib;
import com.github.ipecter.rtu.lib.plugin.RTUListener;
import com.github.ipecter.rtu.lib.plugin.RTUPlugin;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;

public class MotdOnJoin extends RTUListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!RTULib.getInstance().getConfigManager().getSetting().isMotd()) return;
        Map<String, RTUPlugin> plugins = RTULib.getInstance().getPlugins();
        Audience audience = this.getPlugin().getAdventure().player(e.getPlayer());
        for (String key : plugins.keySet()) {
            RTUPlugin plugin = plugins.get(key);
            audience.sendMessage(plugin.getPrefix()
                    .append(MiniMessage.miniMessage().deserialize(
                            plugin.getName() + " developed by " + String.join(" & ", plugin.getDescription().getAuthors())
                    )));
        }
    }
}
