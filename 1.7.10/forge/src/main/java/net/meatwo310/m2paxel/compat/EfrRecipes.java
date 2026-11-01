package net.meatwo310.m2paxel.compat;

import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.recipes.SmithingTableRecipes;
import net.meatwo310.m2paxel.common.item.M2PaxelItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/** Only loaded after checking that Et Futurum Requiem is present. */
public final class EfrRecipes {
    private EfrRecipes() {}

    public static void registerNetheriteUpgrades() {
        if (!ConfigBlocksItems.enableSmithingTable) {
            return;
        }
        SmithingTableRecipes recipes = SmithingTableRecipes.getInstance();
        recipes.addRecipe(new NetheriteUpgradeRecipe(M2PaxelItems.DIAMOND_PAXEL, M2PaxelItems.NETHERITE_PAXEL));
        recipes.addRecipe(new NetheriteUpgradeRecipe(M2PaxelItems.DIAMOND_PHAXEL, M2PaxelItems.NETHERITE_PHAXEL));
    }

    private static final class NetheriteUpgradeRecipe extends SmithingTableRecipes.SmithingTableRecipe {
        private NetheriteUpgradeRecipe(Item input, Item output) {
            super(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), "ingotNetherite", new ItemStack(output), true);
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting input) {
            ItemStack result = super.getCraftingResult(input);
            // EFR shares the input NBT; keep the upgraded tool independent of its source.
            if (result.hasTagCompound()) {
                result.setTagCompound((NBTTagCompound) result.getTagCompound().copy());
            }
            return result;
        }
    }
}
