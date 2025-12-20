package io.github.meatwo310.m2paxel.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import io.github.meatwo310.m2paxel.M2Paxel;
import io.github.meatwo310.m2paxel.Tags;
import io.github.meatwo310.m2paxel.common.item.ItemPaxel;
import net.minecraft.item.Item;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        M2Paxel.LOG.info(Config.greeting);
        M2Paxel.LOG.info("I am M2Paxel at version " + Tags.VERSION);

        GameRegistry.registerItem(new ItemPaxel(Item.ToolMaterial.WOOD).setUnlocalizedName("m2paxel.wood"), "wood_paxel");
        GameRegistry.registerItem(new ItemPaxel(Item.ToolMaterial.STONE).setUnlocalizedName("m2paxel.stone"), "stone_paxel");
        GameRegistry.registerItem(new ItemPaxel(Item.ToolMaterial.IRON).setUnlocalizedName("m2paxel.iron"), "iron_paxel");
        GameRegistry.registerItem(new ItemPaxel(Item.ToolMaterial.GOLD).setUnlocalizedName("m2paxel.gold"), "gold_paxel");
        GameRegistry.registerItem(new ItemPaxel(Item.ToolMaterial.EMERALD).setUnlocalizedName("m2paxel.diamond"), "diamond_paxel");
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
