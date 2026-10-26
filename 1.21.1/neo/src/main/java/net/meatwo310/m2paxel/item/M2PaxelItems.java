package net.meatwo310.m2paxel.item;

import net.meatwo310.m2paxel.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class M2PaxelItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MODID);

    public static final DeferredItem<Item> WOODEN_PAXEL = paxel("wooden_paxel", Tiers.WOOD);
    public static final DeferredItem<Item> STONE_PAXEL = paxel("stone_paxel", Tiers.STONE);
    public static final DeferredItem<Item> IRON_PAXEL = paxel("iron_paxel", Tiers.IRON);
    public static final DeferredItem<Item> GOLDEN_PAXEL = paxel("golden_paxel", Tiers.GOLD);
    public static final DeferredItem<Item> DIAMOND_PAXEL = paxel("diamond_paxel", Tiers.DIAMOND);
    public static final DeferredItem<Item> NETHERITE_PAXEL = paxel("netherite_paxel", Tiers.NETHERITE);

    private M2PaxelItems() {}

    private static DeferredItem<Item> paxel(String name, Tier tier) {
        return ITEMS.registerItem(name, properties -> new PaxelItem(tier, properties));
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
