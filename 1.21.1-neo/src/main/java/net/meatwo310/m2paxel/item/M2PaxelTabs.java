package net.meatwo310.m2paxel.item;

import net.meatwo310.m2paxel.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class M2PaxelTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB =
            TABS.register("m2paxel", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.m2paxel"))
                    .icon(() -> M2PaxelItems.DIAMOND_PAXEL.get().getDefaultInstance())
                    .displayItems(M2PaxelTabs::registerItems)
                    .build());

    private M2PaxelTabs() {}

    private static void registerItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        M2PaxelItems.ITEMS.getEntries().forEach(holder -> output.accept(holder.get()));
    }

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
