package net.meatwo310.m2paxel.client;

import net.meatwo310.m2paxel.Constants;
import net.meatwo310.m2paxel.client.mdk.config.KeyedConfigScreen;
import net.meatwo310.m2paxel.item.PhaxelItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.MODID, dist = Dist.CLIENT)
public class ModClient {
    public ModClient(ModContainer container) {
        NeoForge.EVENT_BUS.addListener(ModClient::onAttributeTooltips);
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) ->
                new ConfigurationScreen(mod, parent, KeyedConfigScreen::new)
        );
    }

    private static void onAttributeTooltips(AddAttributeTooltipsEvent event) {
        if (!(event.getStack().getItem() instanceof PhaxelItem) || event.getStack().has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)) {
            return;
        }
        event.addTooltipLines(
                CommonComponents.EMPTY,
                Component.translatable("tooltip.m2paxel.on_right_click").withStyle(ChatFormatting.GRAY),
                Component.translatable("tooltip.m2paxel.phaxel.actions").withStyle(ChatFormatting.BLUE)
        );
    }
}
