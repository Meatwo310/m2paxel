package net.meatwo310.m2paxel.item;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;

public class PaxelItem extends Item {
    private record Baseline(float attackDamage, float attackSpeed) {}
    /// Copied values from vanilla axe.
    /// @see net.minecraft.world.item.Items
    private static final Map<ToolMaterial, Baseline> AXE_BASELINES = Map.of(
            ToolMaterial.WOOD,      new Baseline(6.0F, -3.2F),
            ToolMaterial.COPPER,    new Baseline(7.0F, -3.2F),
            ToolMaterial.STONE,     new Baseline(7.0F, -3.2F),
            ToolMaterial.GOLD,      new Baseline(6.0F, -3.0F),
            ToolMaterial.IRON,      new Baseline(6.0F, -3.1F),
            ToolMaterial.DIAMOND,   new Baseline(5.0F, -3.0F),
            ToolMaterial.NETHERITE, new Baseline(5.0F, -3.0F)
    );

    public PaxelItem(ToolMaterial material, Properties properties) {
        Baseline baseline = AXE_BASELINES.get(material);
        this(material, baseline.attackDamage, baseline.attackSpeed, properties);
    }

    public PaxelItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        super(build(material, attackDamageBaseline, attackSpeedBaseline, properties));
    }

    private static Properties build(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties base) {
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

        ItemAttributeModifiers attributes = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID,
                                attackDamageBaseline + material.attackDamageBonus(),
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                Item.BASE_ATTACK_SPEED_ID,
                                attackSpeedBaseline,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();

        return base
                .durability(material.durability() * 4)
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponents.WEAPON, new Weapon(2, Weapon.AXE_DISABLES_BLOCKING_FOR_SECONDS))
                .component(DataComponents.TOOL, tool)
                .attributes(attributes);
    }
}
