package net.meatwo310.m2paxel.config;

import net.meatwo310.m2paxel.mdk.config.ConfigEntries;
import net.meatwo310.m2paxel.mdk.config.ConfigEntryBuilder;

public class ServerConfig {
    private static final ConfigEntryBuilder BUILDER = new ConfigEntryBuilder();

    public static final ConfigEntries ENTRIES = BUILDER.build();
}
