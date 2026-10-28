package net.meatwo310.m2paxel.recipe;

import net.meatwo310.m2paxel.common.item.ItemPaxel;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Arrays;

/** Keeps the standard shapeless ingredient list so NEI can display every recipe. */
public final class ToolCraftingRecipe extends ShapelessRecipes {
    private final ToolCraftingOperation operation;

    public ToolCraftingRecipe(ItemStack output, ToolCraftingOperation operation, ItemStack... ingredients) {
        super(output, Arrays.asList(ingredients));
        this.operation = operation;
    }

    @Override
    public boolean matches(InventoryCrafting input, World world) {
        return super.matches(input, world)
                && (operation != ToolCraftingOperation.COMBINE || remainingDurability(input) > 0);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting input) {
        ItemStack result = getRecipeOutput().copy();
        ItemStack base = null;
        for (int slot = 0; slot < input.getSizeInventory(); slot++) {
            ItemStack stack = input.getStackInSlot(slot);
            if (stack != null && stack.getItem() instanceof ItemPaxel) {
                base = stack;
                break;
            }
        }
        if (base != null && base.hasTagCompound()) {
            result.setTagCompound((NBTTagCompound) base.getTagCompound().copy());
        }
        int maximum = result.getMaxDamage();
        int damage = switch (operation) {
            case COMBINE -> ToolDurability.combinedDamage(maximum, remainingDurability(input));
            case REMOVE_HOE -> ToolDurability.scaledDamage(base.getItemDamage(), base.getMaxDamage(), maximum);
        };
        result.setItemDamage(Math.max(0, Math.min(maximum - 1, damage)));
        return result;
    }

    private static long remainingDurability(InventoryCrafting input) {
        long remaining = 0;
        for (int slot = 0; slot < input.getSizeInventory(); slot++) {
            ItemStack stack = input.getStackInSlot(slot);
            if (stack != null && stack.getMaxDamage() > 0) {
                remaining += Math.max(0L, (long) stack.getMaxDamage() - stack.getItemDamage());
            }
        }
        return remaining;
    }
}
