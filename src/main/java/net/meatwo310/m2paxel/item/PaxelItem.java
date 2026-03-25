package net.meatwo310.m2paxel.item;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PaxelItem extends Item {
    public PaxelItem(ToolMaterial material, Properties properties) {
        super(build(material, properties));
    }

    private static Properties build(ToolMaterial material, Properties base) {
        HolderGetter<Block> blocks =
                BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);

        float speed = material.speed();

        Tool tool = new Tool(
                List.of(
                        Tool.Rule.deniesDrops(blocks.getOrThrow(material.incorrectBlocksForDrops())),
                        Tool.Rule.minesAndDrops(blocks.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), speed),
                        Tool.Rule.minesAndDrops(blocks.getOrThrow(BlockTags.MINEABLE_WITH_AXE), speed),
                        Tool.Rule.minesAndDrops(blocks.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), speed)
                ),
                1.0F,
                1,
                true
        );

        return base
                .durability(material.durability() * 4)
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponents.WEAPON, new Weapon(2, Weapon.AXE_DISABLES_BLOCKING_FOR_SECONDS))
                .component(DataComponents.TOOL, tool);
    }
}
