package net.meatwo310.m2paxel.client;

import net.meatwo310.m2paxel.M2Paxel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = M2Paxel.MODID, dist = Dist.CLIENT)
public class M2PaxelClient {
    public M2PaxelClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
