package net.meatwo310.m2paxel.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.meatwo310.m2paxel.item.PaxelItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.Locale;
import java.util.Objects;

public final class ToolCraftingRecipe extends ShapelessRecipe {
    private static final Codec<ToolCraftingOperation> OPERATION_CODEC = Codec.STRING.comapFlatMap(
            name -> {
                try {
                    return DataResult.success(ToolCraftingOperation.valueOf(name.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException exception) {
                    return DataResult.error(() -> "Unknown tool crafting operation: " + name);
                }
            },
            operation -> operation.name().toLowerCase(Locale.ROOT));
    public static final MapCodec<ShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ShapelessRecipe.MAP_CODEC.forGetter(recipe -> ((ToolCraftingRecipe) recipe).displayRecipe),
            OPERATION_CODEC.optionalFieldOf("operation", ToolCraftingOperation.COMBINE)
                    .forGetter(recipe -> ((ToolCraftingRecipe) recipe).operation)
    ).apply(instance, ToolCraftingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) -> {
                ToolCraftingRecipe toolRecipe = (ToolCraftingRecipe) recipe;
                ShapelessRecipe.STREAM_CODEC.encode(buffer, toolRecipe.displayRecipe);
                buffer.writeEnum(toolRecipe.operation);
            },
            buffer -> new ToolCraftingRecipe(ShapelessRecipe.STREAM_CODEC.decode(buffer), buffer.readEnum(ToolCraftingOperation.class)));

    private final ShapelessRecipe displayRecipe;
    private final ToolCraftingOperation operation;

    public ToolCraftingRecipe(ShapelessRecipe recipe, ToolCraftingOperation operation) {
        super(new Recipe.CommonInfo(recipe.showNotification()),
                new CraftingBookInfo(recipe.category(), recipe.group()),
                Objects.requireNonNull(recipe.result()), recipe.placementInfo().ingredients());
        this.displayRecipe = recipe;
        this.operation = operation;
    }

    @Override
    public RecipeSerializer<ShapelessRecipe> getSerializer() {
        return M2PaxelRecipes.TOOL_CRAFTING.get();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return super.matches(input, level)
                && (operation != ToolCraftingOperation.COMBINE || remainingDurability(input) > 0);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack result = displayRecipe.assemble(input);
        ItemStack base = ItemStack.EMPTY;
        for (ItemStack stack : input.items()) {
            if (stack.getItem() instanceof PaxelItem) {
                base = stack;
                break;
            }
        }
        if (!base.isEmpty()) {
            // Copy only per-stack changes; target item defaults retain its own tool stats.
            result.applyComponents(base.getComponentsPatch());
        }
        int maximum = result.getMaxDamage();
        int damage = switch (operation) {
            case COMBINE -> ToolDurability.combinedDamage(maximum, remainingDurability(input));
            case REMOVE_HOE -> ToolDurability.scaledDamage(base.getDamageValue(), base.getMaxDamage(), maximum);
        };
        result.setDamageValue(Math.max(0, Math.min(maximum - 1, damage)));
        return result;
    }

    private static long remainingDurability(CraftingInput input) {
        long remaining = 0;
        for (ItemStack stack : input.items()) {
            if (!stack.isEmpty() && stack.getMaxDamage() > 0) {
                remaining += Math.max(0L, (long) stack.getMaxDamage() - stack.getDamageValue());
            }
        }
        return remaining;
    }
}
