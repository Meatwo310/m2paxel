package io.github.meatwo310.m2paxel.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import io.github.meatwo310.m2paxel.M2Paxel;
import io.github.meatwo310.m2paxel.Tags;
import io.github.meatwo310.m2paxel.common.item.M2PaxelItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CommonProxy {
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        M2Paxel.LOG.info(Config.greeting);
        M2Paxel.LOG.info("I am M2Paxel at version " + Tags.VERSION);

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
//        Item[][] recipes = new Item[][]{
//            {
//                M2PaxelItems.WOODEN_PAXEL,
//                Items.wooden_pickaxe,
//                Items.wooden_axe,
//                Items.wooden_shovel,
//                Items.wooden_sword
//            },
//            {
//                M2PaxelItems.STONE_PAXEL,
//                Items.stone_pickaxe,
//                Items.stone_axe,
//                Items.stone_shovel,
//                Items.stone_sword
//            },
//            {
//                M2PaxelItems.IRON_PAXEL,
//                Items.iron_pickaxe,
//                Items.iron_axe,
//                Items.iron_shovel,
//                Items.iron_sword
//            },
//            {
//                M2PaxelItems.GOLDEN_PAXEL,
//                Items.golden_pickaxe,
//                Items.golden_axe,
//                Items.golden_shovel,
//                Items.golden_sword
//            },
//            {
//                M2PaxelItems.DIAMOND_PAXEL,
//                Items.diamond_pickaxe,
//                Items.diamond_axe,
//                Items.diamond_shovel,
//                Items.diamond_sword
//            }
//        };
//
//        for (Item[] recipe : recipes) {
//            GameRegistry.addShapedRecipe(
//                // output
//                new ItemStack(recipe[0]),
//
//                // pattern
//                "W P",
//                " X ",
//                "S A",
//
//                // ingredients
//                'X', new ItemStack(Items.stick),
//                'P', new ItemStack(recipe[1]), // pickaxe
//                'A', new ItemStack(recipe[2]), // axe
//                'S', new ItemStack(recipe[3]), // shovel
//                'W', new ItemStack(recipe[4])  // sword
//            );
//        }

        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.WOODEN_PAXEL),
            Items.wooden_sword,
            Items.wooden_pickaxe,
            Items.wooden_shovel,
            Items.wooden_axe
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.STONE_PAXEL),
            Items.stone_sword,
            Items.stone_pickaxe,
            Items.stone_shovel,
            Items.stone_axe
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.IRON_PAXEL),
            Items.iron_sword,
            Items.iron_pickaxe,
            Items.iron_shovel,
            Items.iron_axe
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.GOLDEN_PAXEL),
            Items.golden_sword,
            Items.golden_pickaxe,
            Items.golden_shovel,
            Items.golden_axe
        );
        GameRegistry.addShapelessRecipe(
            new ItemStack(M2PaxelItems.DIAMOND_PAXEL),
            Items.diamond_sword,
            Items.diamond_pickaxe,
            Items.diamond_shovel,
            Items.diamond_axe
        );
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
