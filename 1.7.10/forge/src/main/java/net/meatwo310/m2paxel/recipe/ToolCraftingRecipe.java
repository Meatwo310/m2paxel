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
                && getCraftingResult(input) != null;
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
        long damage = switch (operation) {
            case COMBINE -> totalDamage(input);
            case REMOVE_HOE -> ToolDurability.scaledDamage(base.getItemDamage(), base.getMaxDamage(), maximum);
        };
        if (operation == ToolCraftingOperation.COMBINE && damage >= maximum) {
            return null;
        }
        result.setItemDamage((int) Math.max(0L, Math.min(maximum - 1L, damage)));
        return result;
    }

    private static long totalDamage(InventoryCrafting input) {
        long damage = 0;
        for (int slot = 0; slot < input.getSizeInventory(); slot++) {
            ItemStack stack = input.getStackInSlot(slot);
            if (stack != null) {
                damage += Math.max(0, stack.getItemDamage());
            }
        }
        return damage;
    }
}
