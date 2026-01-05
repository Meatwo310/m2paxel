package io.github.meatwo310.m2paxel.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.meatwo310.m2paxel.M2Paxel;
import io.github.meatwo310.m2paxel.common.CommonConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;

public class CommonEvents {
    public static final String PREFIX = M2Paxel.MODID + ":";

    /**
     * Registers the common event handlers.
     * Call this method once during init phase on the common proxy.
     */
    public static void register() {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!CommonConfig.EASY_REPAIR.get()) {
            return;
        }

        Item left = event.left.getItem();
        if (left == null) {
            return;
        }

        String leftRegistryName = Item.itemRegistry.getNameForObject(left);
        if (!leftRegistryName.startsWith(PREFIX)) {
            return;
        }

        ItemStack inputStack = event.left;
        ItemStack materialStack = event.right;

        if (materialStack == null) return;
        if (!inputStack.isItemStackDamageable() || !inputStack.getItem().getIsRepairable(inputStack, materialStack)) {
            return;
        }

        int repairPerItem = inputStack.getMaxDamage() / 4;
        int currentDamage = inputStack.getItemDamage();
        if (currentDamage <= 0) return;
        int materialUsed = 0;
        int newDamage = currentDamage;

        for (int i = 0; i < materialStack.stackSize; i++) {
            if (newDamage > 0) {
                newDamage -= repairPerItem;
                materialUsed++;
            } else {
                break;
            }
        }

        if (newDamage < 0) newDamage = 0;

        event.output = inputStack.copy();
        event.output.setItemDamage(newDamage);

        if (event.name != null && !event.name.isEmpty()) {
            event.output.setStackDisplayName(event.name);
        }

        event.materialCost = materialUsed;
        event.cost = 1;
    }
}
