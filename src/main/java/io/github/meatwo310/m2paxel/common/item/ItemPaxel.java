package io.github.meatwo310.m2paxel.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import io.github.meatwo310.m2paxel.M2Paxel;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemPaxel extends ItemPickaxe {
    private static final Set<String> toolClasses = ImmutableSet.of("pickaxe", "axe", "shovel");

    private final double weaponDamage;

    public ItemPaxel(ToolMaterial material, String itemId) {
        this(material, M2Paxel.MODID, itemId);
    }

    public ItemPaxel(ToolMaterial material, String modId, String itemId) {
        super(material);

        this.setUnlocalizedName(modId + "." + itemId);
        this.setTextureName(modId + ":" + itemId);

        this.setMaxDamage(material.getMaxUses() * 4);
        this.setHarvestLevel("axe", material.getHarvestLevel());
        this.setHarvestLevel("shovel", material.getHarvestLevel());

        this.weaponDamage = 4.0D + material.getDamageVsEntity();
    }

    // thanks mekanism
    // https://github.com/mekanism/Mekanism/blob/611627519d0c2506028869a00d50488a6ab64fac/src/main/java/mekanism/tools/item/ItemMekanismPaxel.java#L15-L19
    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return block != Blocks.bedrock ? this.efficiencyOnProperMaterial : 1.0F;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        if (block == Blocks.snow_layer || block == Blocks.snow) {
            return true;
        }

        return super.canHarvestBlock(block, stack);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return toolClasses;
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
            new AttributeModifier(field_111210_e, "Weapon modifier", this.weaponDamage, 0));

        return multimap;
    }
}
