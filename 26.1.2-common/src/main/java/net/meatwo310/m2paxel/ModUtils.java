package net.meatwo310.m2paxel;

import net.minecraft.resources.Identifier;

public final class ModUtils {
    private ModUtils() {}

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Constants.MODID, path);
    }
}
