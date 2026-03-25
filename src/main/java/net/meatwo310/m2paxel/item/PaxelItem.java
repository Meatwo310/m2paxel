package net.meatwo310.m2paxel.item;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;

public class PaxelItem extends Item {
    /// Copied values from vanilla axe.
    /// @see net.minecraft.world.item.Items
    private static final Map<ToolMaterial, Float> AXE_ATTACK_DAMAGES = Map.of(
            ToolMaterial.WOOD,      6.0F,
            ToolMaterial.COPPER,    7.0F,
            ToolMaterial.STONE,     7.0F,
            ToolMaterial.GOLD,      6.0F,
            ToolMaterial.IRON,      6.0F,
            ToolMaterial.DIAMOND,   5.0F,
            ToolMaterial.NETHERITE, 5.0F
    );
    /// Copied values from vanilla sword.
    /// @see net.minecraft.world.item.Items
    private static final float SWORD_ATTACK_SPEED = -2.4F;

    public PaxelItem(ToolMaterial material, Properties properties) {
        this(material, AXE_ATTACK_DAMAGES.get(material), SWORD_ATTACK_SPEED, properties);
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
                        Tool.Rule.minesAndDrops(blocks.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), speed),
                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                        Tool.Rule.overrideSpeed(blocks.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                        Tool.Rule.overrideSpeed(blocks.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
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

    @Override
    public boolean canPerformAction(@NonNull ItemInstance stack, @NonNull ItemAbility ability) {
        return ability == ItemAbilities.SWORD_SWEEP || super.canPerformAction(stack, ability);
    }
}
