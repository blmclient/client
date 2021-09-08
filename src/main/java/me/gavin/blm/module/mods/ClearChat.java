package me.gavin.blm.module.mods;

import me.gavin.blm.events.GuiChatBackgroundEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(
        name = "ClearChat",
        description = "Makes the chat window transparent",
        category = Module.Category.Chat
)
public final class ClearChat extends Module {

    @SubscribeEvent
    public void chatBackground(GuiChatBackgroundEvent event) {
        event.setCanceled(true);
    }
}
