package net.meatwo310.m2paxel.compat;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/** Discovered by UniMixins without being loaded by the normal mod entry point. */
@LateMixin
public final class EfrLateMixins implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.m2paxel.efr.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return loadedMods.contains("etfuturum")
            ? Collections.singletonList("MixinEfrServerEventHandler")
            : Collections.emptyList();
    }
}
