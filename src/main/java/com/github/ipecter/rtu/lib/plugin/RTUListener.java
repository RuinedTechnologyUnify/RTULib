package com.github.ipecter.rtu.lib.plugin;

import lombok.Getter;
import org.bukkit.event.Listener;

public abstract class RTUListener implements Listener {
    @Getter
    private final RTUPlugin plugin = RTUPlugin.getPlugin();
}
