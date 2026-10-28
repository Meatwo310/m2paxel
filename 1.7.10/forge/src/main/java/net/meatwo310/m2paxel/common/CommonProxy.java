package net.meatwo310.m2paxel.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.meatwo310.m2paxel.common.event.CommonEvents;
import net.meatwo310.m2paxel.common.item.M2PaxelItems;
import net.meatwo310.m2paxel.recipe.ToolCraftingOperation;
import net.meatwo310.m2paxel.recipe.ToolCraftingRecipe;
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

        GameRegistry.registerItem(M2PaxelItems.WOODEN_PHAXEL, "wooden_phaxel");
        GameRegistry.registerItem(M2PaxelItems.STONE_PHAXEL, "stone_phaxel");
        GameRegistry.registerItem(M2PaxelItems.IRON_PHAXEL, "iron_phaxel");
        GameRegistry.registerItem(M2PaxelItems.GOLDEN_PHAXEL, "golden_phaxel");
        GameRegistry.registerItem(M2PaxelItems.DIAMOND_PHAXEL, "diamond_phaxel");
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        registerToolRecipes();
        CommonEvents.register();
    }

    private static void registerToolRecipes() {
        registerToolRecipes(M2PaxelItems.WOODEN_PAXEL, M2PaxelItems.WOODEN_PHAXEL,
            Items.wooden_sword, Items.wooden_pickaxe, Items.wooden_shovel, Items.wooden_axe, Items.wooden_hoe);
        registerToolRecipes(M2PaxelItems.STONE_PAXEL, M2PaxelItems.STONE_PHAXEL,
            Items.stone_sword, Items.stone_pickaxe, Items.stone_shovel, Items.stone_axe, Items.stone_hoe);
        registerToolRecipes(M2PaxelItems.IRON_PAXEL, M2PaxelItems.IRON_PHAXEL,
            Items.iron_sword, Items.iron_pickaxe, Items.iron_shovel, Items.iron_axe, Items.iron_hoe);
        registerToolRecipes(M2PaxelItems.GOLDEN_PAXEL, M2PaxelItems.GOLDEN_PHAXEL,
            Items.golden_sword, Items.golden_pickaxe, Items.golden_shovel, Items.golden_axe, Items.golden_hoe);
        registerToolRecipes(M2PaxelItems.DIAMOND_PAXEL, M2PaxelItems.DIAMOND_PHAXEL,
            Items.diamond_sword, Items.diamond_pickaxe, Items.diamond_shovel, Items.diamond_axe, Items.diamond_hoe);
    }

    private static void registerToolRecipes(Item paxel, Item phaxel, Item sword, Item pickaxe,
                                            Item shovel, Item axe, Item hoe) {
        GameRegistry.addRecipe(new ToolCraftingRecipe(new ItemStack(paxel), ToolCraftingOperation.COMBINE,
            ignoreDamage(sword), ignoreDamage(pickaxe), ignoreDamage(shovel), ignoreDamage(axe)));
        GameRegistry.addRecipe(new ToolCraftingRecipe(new ItemStack(phaxel), ToolCraftingOperation.COMBINE,
            ignoreDamage(sword), ignoreDamage(pickaxe), ignoreDamage(shovel), ignoreDamage(axe), ignoreDamage(hoe)));
        GameRegistry.addRecipe(new ToolCraftingRecipe(new ItemStack(phaxel), ToolCraftingOperation.COMBINE,
            ignoreDamage(paxel), ignoreDamage(hoe)));
        GameRegistry.addRecipe(new ToolCraftingRecipe(new ItemStack(paxel), ToolCraftingOperation.REMOVE_HOE,
            ignoreDamage(phaxel)));
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}

    private static ItemStack ignoreDamage(Item item) {
        return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
    }
}
