package net.meatwo310.m2paxel.item;

import net.meatwo310.m2paxel.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class M2PaxelItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MODID);

    public static final DeferredItem<Item> WOODEN_PAXEL = paxel("wooden_paxel", ToolMaterial.WOOD);
    public static final DeferredItem<Item> STONE_PAXEL = paxel("stone_paxel", ToolMaterial.STONE);
    public static final DeferredItem<Item> COPPER_PAXEL = paxel("copper_paxel", ToolMaterial.COPPER);
    public static final DeferredItem<Item> IRON_PAXEL = paxel("iron_paxel", ToolMaterial.IRON);
    public static final DeferredItem<Item> GOLDEN_PAXEL = paxel("golden_paxel", ToolMaterial.GOLD);
    public static final DeferredItem<Item> DIAMOND_PAXEL = paxel("diamond_paxel", ToolMaterial.DIAMOND);
    public static final DeferredItem<Item> NETHERITE_PAXEL = paxel("netherite_paxel", ToolMaterial.NETHERITE);

    private static DeferredItem<Item> paxel(String name, ToolMaterial material) {
        return ITEMS.registerItem(name, properties -> new PaxelItem(material, properties));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
