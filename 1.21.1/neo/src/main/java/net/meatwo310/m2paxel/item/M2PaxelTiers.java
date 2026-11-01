package net.meatwo310.m2paxel.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public final class M2PaxelTiers {
    public static final Tier COPPER = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL,
            190, 5.0F, 1.0F, 13, () -> Ingredient.of(Items.COPPER_INGOT));

    private M2PaxelTiers() {}
}
