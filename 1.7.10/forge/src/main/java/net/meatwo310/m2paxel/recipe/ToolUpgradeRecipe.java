package net.meatwo310.m2paxel.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/** Surrounds a tool with eight ingots while preserving its damage and NBT. */
public final class ToolUpgradeRecipe extends ShapedOreRecipe {
    public ToolUpgradeRecipe(Item output, Item input, String ingot) {
        super(new ItemStack(output), "III", "ITI", "III",
            'I', ingot, 'T', new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public boolean matches(InventoryCrafting input, World world) {
        return super.matches(input, world) && getCraftingResult(input) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting input) {
        return ToolCraftingRecipe.getCraftingResult(input, getRecipeOutput(), ToolCraftingOperation.COMBINE);
    }
}
