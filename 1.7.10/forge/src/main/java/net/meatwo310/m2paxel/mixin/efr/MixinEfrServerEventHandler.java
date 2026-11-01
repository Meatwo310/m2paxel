package net.meatwo310.m2paxel.mixin.efr;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.meatwo310.m2paxel.common.item.ItemPaxel;
import net.meatwo310.m2paxel.common.item.ItemPhaxel;
import net.meatwo310.m2paxel.compat.EfrToolActions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;

@Pseudo
@Mixin(targets = "ganymedes01.etfuturum.core.handlers.ServerEventHandler", remap = false)
public abstract class MixinEfrServerEventHandler {
    @Inject(method = "onPlayerInteract", at = @At("HEAD"), cancellable = true, remap = false)
    private void m2paxel$tillGrassPath(PlayerInteractEvent event, CallbackInfo callback) {
        // Canceling the client event would prevent the interaction packet from reaching the server.
        if (event.world.isRemote || event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
                || event.useItem == Result.DENY) {
            return;
        }
        ItemStack held = event.entityPlayer.getHeldItem();
        // Check before EFR creates a path, so grass -> path -> farmland takes two clicks.
        if (held != null && held.getItem() instanceof ItemPhaxel
                && EfrToolActions.isGrassPath(event.world.getBlock(event.x, event.y, event.z))
                && EfrToolActions.tillGrassPath(held, event.entityPlayer, event.world,
                    event.x, event.y, event.z, event.face)) {
            event.entityPlayer.swingItem();
            event.setCanceled(true);
            callback.cancel();
        }
    }

    @ModifyExpressionValue(
        method = "onPlayerInteract",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/item/Item;getToolClasses(Lnet/minecraft/item/ItemStack;)Ljava/util/Set;",
            remap = false),
        remap = false,
        require = 1
    )
    private Set<String> m2paxel$filterRightClickToolClasses(Set<String> original, PlayerInteractEvent event) {
        ItemStack held = event.entityPlayer.getHeldItem();
        Item item = held.getItem();
        if (item instanceof ItemPaxel && !((ItemPaxel) item).allowsRightClickToolActions()) {
            return Collections.emptySet();
        }
        return original;
    }
}
