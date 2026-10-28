package net.meatwo310.m2paxel.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaxelItem extends Item {
    private static final Map<Tier, Float> AXE_ATTACK_DAMAGES = Map.of(
            Tiers.WOOD, 6.0F,
            Tiers.STONE, 7.0F,
            Tiers.GOLD, 6.0F,
            Tiers.IRON, 6.0F,
            Tiers.DIAMOND, 5.0F,
            Tiers.NETHERITE, 5.0F
    );
    private static final Set<ItemAbility> PAXEL_ACTIONS = Set.of(
            ItemAbilities.AXE_DIG,
            ItemAbilities.PICKAXE_DIG,
            ItemAbilities.SHOVEL_DIG,
            ItemAbilities.SWORD_DIG,
            ItemAbilities.SWORD_SWEEP
    );
    private static final float SWORD_ATTACK_SPEED = -2.4F;
    private final Tier tier;

    public PaxelItem(Tier tier, Properties properties) {
        this(tier, AXE_ATTACK_DAMAGES.get(tier), SWORD_ATTACK_SPEED, properties);
    }

    public PaxelItem(Tier tier, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        this(tier, attackDamageBaseline, attackSpeedBaseline, properties, false);
    }

    protected PaxelItem(Tier tier, Properties properties, boolean includesHoe) {
        this(tier, AXE_ATTACK_DAMAGES.get(tier), SWORD_ATTACK_SPEED, properties, includesHoe);
    }

    private PaxelItem(Tier tier, float attackDamageBaseline, float attackSpeedBaseline, Properties properties, boolean includesHoe) {
        super(build(tier, attackDamageBaseline, attackSpeedBaseline, properties, includesHoe));
        this.tier = tier;
    }

    private static Properties build(Tier tier, float attackDamageBaseline, float attackSpeedBaseline, Properties base, boolean includesHoe) {
        float speed = tier.getSpeed();

        List<Tool.Rule> rules = new ArrayList<>(List.of(
                Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_PICKAXE, speed),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, speed),
                Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_SHOVEL, speed),
                Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
                Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)
        ));
        if (includesHoe) {
            rules.add(4, Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_HOE, speed));
        }

        Tool tool = new Tool(
                rules,
                1.0F,
                1
        );

        ItemAttributeModifiers attributes = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID,
                                attackDamageBaseline + tier.getAttackDamageBonus(),
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
                .durability(tier.getUses() * (includesHoe ? 5 : 4))
                .component(DataComponents.TOOL, tool)
                .attributes(attributes);
    }

    @Override
    public boolean canPerformAction(@NonNull ItemStack stack, @NonNull ItemAbility ability) {
        return PAXEL_ACTIONS.contains(ability) || super.canPerformAction(stack, ability);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return this.tier.getRepairIngredient().test(repairCandidate) || super.isValidRepairItem(stack, repairCandidate);
    }
}
