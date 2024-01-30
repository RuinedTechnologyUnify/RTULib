package com.github.ipecter.rtu.lib.commands;

import com.github.ipecter.rtu.lib.RTULib;
import com.github.ipecter.rtu.lib.managers.ConfigManager;
import com.github.ipecter.rtu.lib.plugin.RTUCommand;
import com.github.ipecter.rtu.lib.plugin.command.CommandData;
import com.github.ipecter.rtu.lib.util.common.ComponentUtil;

import java.util.ArrayList;
import java.util.List;

public class Command extends RTUCommand {

    private final ConfigManager configManager = RTULib.getInstance().getConfigManager();

    public Command() {
        super("rtulib");
    }

    @Override
    public void command(CommandData data) {
        if (data.length(1) && data.args(0).equalsIgnoreCase("reload")) {
            if (hasPermission("rtulib.reload")) {
                configManager.init();
                sendMessage(ComponentUtil.formatted(getSender(), configManager.getTranslation("prefix") + configManager.getTranslation("reloadMsg")));
            } else {
                sendMessage(ComponentUtil.formatted(getSender(), configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
            }
        } else {
            if (isOp()) {
                sendMessage(ComponentUtil.formatted(getSender(), configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageOp")));

            } else {
                sendMessage(ComponentUtil.formatted(getSender(), configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsage")));
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandData data) {
        if (data.length(1)) {
            List<String> list = new ArrayList<>();
            if (hasPermission("rtulib.reload")) {
                list.add("reload");
            }
            return list;
        }
        return List.of();
    }
}
