package com.example.relief.config;

import com.example.relief.utils.SpUtils;

public final class Config {

    public static final String SETTING_CONFIG = "SettingConfig";

    private static SpUtils sSp = SpUtils.getInstance(SETTING_CONFIG);

    private Config() {
    }

    public static void resetConfig() {
        SpUtils.getInstance(SETTING_CONFIG).clear();
        loadConfig();
    }

    public static void loadConfig() {
    }

    static {
        loadConfig();
    }
}
