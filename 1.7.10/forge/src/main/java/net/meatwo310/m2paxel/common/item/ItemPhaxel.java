package net.meatwo310.m2paxel.common.item;

import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public final class ItemPhaxel extends ItemPaxel {
    private static final Set<String> TOOL_CLASSES = ImmutableSet.of("pickaxe", "axe", "shovel", "hoe");

    public ItemPhaxel(ToolMaterial material, String itemId) {
        super(material, itemId);
        setMaxDamage(material.getMaxUses() * 5);
        setHarvestLevel("hoe", material.getHarvestLevel());
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return TOOL_CLASSES;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("tooltip.m2paxel.phaxel.till"));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return Items.wooden_hoe.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
