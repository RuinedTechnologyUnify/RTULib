package com.github.ipecter.rtu.lib.managers;

import com.github.ipecter.rtu.lib.RTULib;
import com.github.ipecter.rtu.lib.managers.config.SettingConfig;
import com.github.ipecter.rtu.lib.util.common.FileUtil;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    @Getter
    private final SettingConfig setting = new SettingConfig();
    private final Map<String, String> msgKeyMap = Collections.synchronizedMap(new HashMap<>());

    public void init() {
        initSetting(FileUtil.copyResource(RTULib.getInstance(), "Setting.yml"));
        initMessage(FileUtil.copyResource(RTULib.getInstance(), "Translations", "Locale_" + setting.getLocale() + ".yml"));
    }

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        setting.setEnablePlugin(config.getBoolean("enablePlugin", setting.isEnablePlugin()));
        setting.setMotd(config.getBoolean("motd", setting.isMotd()));
        setting.setLocale(config.getString("locale", setting.getLocale()));
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        msgKeyMap.clear();
        for (String key : config.getKeys(false)) {
            if (key.equals("prefix")) {
                String prefixText = config.getString("prefix", "");
                msgKeyMap.put(key, prefixText.isEmpty() ? MiniMessage.miniMessage().serialize(RTULib.getInstance().getPrefix()) : prefixText);
            } else {
                msgKeyMap.put(key, config.getString(key));
            }
        }
        FileUtil.copyResource(RTULib.getInstance(), "Translations", "Locale_EN.yml");
        FileUtil.copyResource(RTULib.getInstance(), "Translations", "Locale_KR.yml");
    }

    public String getTranslation(String key) {
        return msgKeyMap.getOrDefault(key, "");
    }
}
