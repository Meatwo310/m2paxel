package net.meatwo310.m2paxel.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

import java.util.Optional;

/** Shaped tool upgrades retain the same damage and component rules as tool crafting. */
public final class ToolUpgradeRecipe extends ShapedRecipe {
    public static final MapCodec<ShapedRecipe> CODEC = new ShapedRecipe.Serializer().codec().xmap(
            ToolUpgradeRecipe::new, recipe -> ((ToolUpgradeRecipe) recipe).displayRecipe);
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedRecipe> STREAM_CODEC =
            ShapedRecipe.Serializer.STREAM_CODEC.map(
                    ToolUpgradeRecipe::new, recipe -> ((ToolUpgradeRecipe) recipe).displayRecipe);

    private final ShapedRecipe displayRecipe;

    public ToolUpgradeRecipe(ShapedRecipe recipe) {
        super(recipe.getGroup(), recipe.category(),
                new ShapedRecipePattern(recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), Optional.empty()),
                recipe.getResultItem(null), recipe.showNotification());
        this.displayRecipe = recipe;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return M2PaxelRecipes.TOOL_UPGRADE.get();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return super.matches(input, level) && !assemble(input, level.registryAccess()).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return ToolCraftingRecipe.assemble(input, displayRecipe.assemble(input, registries), ToolCraftingOperation.COMBINE);
    }
}
