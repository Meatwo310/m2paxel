package net.meatwo310.m2paxel.recipe;

import net.meatwo310.m2paxel.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class M2PaxelRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Constants.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShapelessRecipe>> TOOL_CRAFTING =
            SERIALIZERS.register("tool_crafting", () -> new RecipeSerializer<>(ToolCraftingRecipe.CODEC, ToolCraftingRecipe.STREAM_CODEC));

    private M2PaxelRecipes() {}

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
