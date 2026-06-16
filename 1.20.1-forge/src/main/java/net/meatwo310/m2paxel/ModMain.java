package net.meatwo310.m2paxel;

import net.meatwo310.m2paxel.config.ModConfigs;
import net.meatwo310.m2paxel.mdk.config.PlatformConfigRegistrar;
import net.meatwo310.m2paxel.mdk.config.VersionedConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MODID)
public class ModMain {
    public ModMain(FMLJavaModLoadingContext ctx) {
        Constants.LOGGER.debug(Constants.INITIALIZING, ModUtils.loc("1.20.1-forge"));
        PlatformConfigRegistrar.registerAll(ctx, VersionedConfigSpec.bindAll(ModConfigs.ALL));
    }
}
