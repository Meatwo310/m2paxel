package net.meatwo310.m2paxel.compat;

import cpw.mods.fml.common.eventhandler.Event.Result;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

/** Only loaded when Et Futurum Requiem is present. */
public final class EfrToolActions {
    private EfrToolActions() {}

    public static boolean isGrassPath(Block block) {
        return ModBlocks.GRASS_PATH.isEnabled() && block == ModBlocks.GRASS_PATH.get();
    }

    /** Mirrors vanilla hoe use, with EFR's grass path as the tillable block. */
    public static boolean tillGrassPath(ItemStack stack, EntityPlayer player, World world,
                                        int x, int y, int z, int side) {
        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }
        UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }
        if (event.getResult() == Result.ALLOW) {
            if (!world.isRemote) {
                stack.damageItem(1, player);
            }
            return true;
        }
        if (event.getResult() == Result.DENY || side == 0
                || !world.isAirBlock(x, y + 1, z) || !isGrassPath(world.getBlock(x, y, z))) {
            return false;
        }
        Block farmland = Blocks.farmland;
        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D,
            farmland.stepSound.getStepResourcePath(),
            (farmland.stepSound.getVolume() + 1.0F) / 2.0F, farmland.stepSound.getPitch() * 0.8F);
        if (!world.isRemote && world.setBlock(x, y, z, farmland)) {
            stack.damageItem(1, player);
        }
        return true;
    }
}
