package net.meatwo310.m2paxel;

import net.meatwo310.m2paxel.config.ServerConfig;
import net.meatwo310.m2paxel.item.M2PaxelItems;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(M2Paxel.MODID)
public class M2Paxel {
    public static final String MODID = "m2paxel";

    public M2Paxel(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        M2PaxelItems.register(modEventBus);
    }

    /**
     * Utility method to create a {@link Identifier} with the namespace of this mod.
     * @param path Path of the resource. Example: {@code "example_item"}
     * @return {@link Identifier} with the namespace of this mod and the given path. Example: {@code examplemod:example_item}
     */
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
