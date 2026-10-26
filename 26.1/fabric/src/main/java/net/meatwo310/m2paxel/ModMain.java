package net.meatwo310.m2paxel;

import net.fabricmc.api.ModInitializer;
import net.meatwo310.m2paxel.config.ModConfigs;
import net.meatwo310.m2paxel.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.m2paxel.mdk.config.VersionedConfigSpec;

public class ModMain implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.id("26.1-fabric"));
        PlatformConfigRegistrar.registerAll(Constants.MODID, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }
}
