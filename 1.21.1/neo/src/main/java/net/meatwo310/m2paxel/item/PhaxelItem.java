package net.meatwo310.m2paxel.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jspecify.annotations.NonNull;

public final class PhaxelItem extends PaxelItem {
    public PhaxelItem(Tier tier, Properties properties) {
        super(tier, properties, true);
    }

    @Override
    public boolean canPerformAction(@NonNull ItemStack stack, @NonNull ItemAbility ability) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_HOE_ACTIONS.contains(ability)
                || super.canPerformAction(stack, ability);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = Items.WOODEN_AXE.useOn(context);
        if (result != InteractionResult.PASS) {
            return result;
        }

        // Path first, then till on the next use: grass -> dirt path -> farmland.
        result = Items.WOODEN_SHOVEL.useOn(context);
        if (result != InteractionResult.PASS) {
            return result;
        }
        return Items.WOODEN_HOE.useOn(context);
    }
}
