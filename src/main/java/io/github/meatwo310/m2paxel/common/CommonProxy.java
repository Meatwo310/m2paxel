package io.github.meatwo310.m2paxel.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import io.github.meatwo310.m2paxel.common.item.M2PaxelItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        CommonConfig.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        GameRegistry.registerItem(M2PaxelItems.WOODEN_PAXEL, "wooden_paxel");
        GameRegistry.registerItem(M2PaxelItems.STONE_PAXEL, "stone_paxel");
        GameRegistry.registerItem(M2PaxelItems.IRON_PAXEL, "iron_paxel");
        GameRegistry.registerItem(M2PaxelItems.GOLDEN_PAXEL, "golden_paxel");
        GameRegistry.registerItem(M2PaxelItems.DIAMOND_PAXEL, "diamond_paxel");
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        registerPaxelRecipe();
    }

    private static void registerPaxelRecipe() {
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.WOODEN_PAXEL),
            ignoreDamage(Items.wooden_sword),
            ignoreDamage(Items.wooden_pickaxe),
            ignoreDamage(Items.wooden_shovel),
            ignoreDamage(Items.wooden_axe)
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.STONE_PAXEL),
            ignoreDamage(Items.stone_sword),
            ignoreDamage(Items.stone_pickaxe),
            ignoreDamage(Items.stone_shovel),
            ignoreDamage(Items.stone_axe)
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.IRON_PAXEL),
            ignoreDamage(Items.iron_sword),
            ignoreDamage(Items.iron_pickaxe),
            ignoreDamage(Items.iron_shovel),
            ignoreDamage(Items.iron_axe)
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.GOLDEN_PAXEL),
            ignoreDamage(Items.golden_sword),
            ignoreDamage(Items.golden_pickaxe),
            ignoreDamage(Items.golden_shovel),
            ignoreDamage(Items.golden_axe)
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.DIAMOND_PAXEL),
            ignoreDamage(Items.diamond_sword),
            ignoreDamage(Items.diamond_pickaxe),
            ignoreDamage(Items.diamond_shovel),
            ignoreDamage(Items.diamond_axe)
        );
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}

    private static ItemStack ignoreDamage(Item item) {
        return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
    }
}
