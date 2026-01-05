package io.github.meatwo310.m2paxel.common;

import io.github.meatwo310.m2paxel.util.config.Config;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class CommonConfig {
    public static final Config<Boolean> EASY_REPAIR = new Config<>(true);

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        EASY_REPAIR.set(configuration.getBoolean(
            "easyRepair",
            Configuration.CATEGORY_GENERAL,
            EASY_REPAIR.getDefault(),
            "Sets the anvil repair cost for paxels to 1 XP level."
        ));

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
