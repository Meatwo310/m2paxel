package net.meatwo310.m2paxel.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.meatwo310.m2paxel.item.PaxelItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.Locale;

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
            new ShapelessRecipe.Serializer().codec().forGetter(recipe -> ((ToolCraftingRecipe) recipe).displayRecipe),
            OPERATION_CODEC.optionalFieldOf("operation", ToolCraftingOperation.COMBINE)
                    .forGetter(recipe -> ((ToolCraftingRecipe) recipe).operation)
    ).apply(instance, ToolCraftingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) -> {
                ToolCraftingRecipe toolRecipe = (ToolCraftingRecipe) recipe;
                ShapelessRecipe.Serializer.STREAM_CODEC.encode(buffer, toolRecipe.displayRecipe);
                buffer.writeEnum(toolRecipe.operation);
            },
            buffer -> new ToolCraftingRecipe(ShapelessRecipe.Serializer.STREAM_CODEC.decode(buffer), buffer.readEnum(ToolCraftingOperation.class)));

    private final ShapelessRecipe displayRecipe;
    private final ToolCraftingOperation operation;

    public ToolCraftingRecipe(ShapelessRecipe recipe, ToolCraftingOperation operation) {
        super(recipe.getGroup(), recipe.category(), recipe.getResultItem(null), recipe.getIngredients());
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
                && !assemble(input, level.registryAccess()).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack result = displayRecipe.assemble(input, registries);
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
        long damage = switch (operation) {
            case COMBINE -> totalDamage(input);
            case REMOVE_HOE -> ToolDurability.scaledDamage(base.getDamageValue(), base.getMaxDamage(), maximum);
        };
        if (operation == ToolCraftingOperation.COMBINE && damage >= maximum) {
            return ItemStack.EMPTY;
        }
        result.setDamageValue((int) Math.max(0L, Math.min(maximum - 1L, damage)));
        return result;
    }

    private static long totalDamage(CraftingInput input) {
        long damage = 0;
        for (ItemStack stack : input.items()) {
            if (!stack.isEmpty()) {
                damage += Math.max(0, stack.getDamageValue());
            }
        }
        return damage;
    }
}
