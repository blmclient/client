package me.gavin.blm.module.mods;

import me.gavin.blm.events.PlayerChatEvent;
import me.gavin.blm.module.Module;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

@Module.Info(
        name = "ChatSuffix",
        description = "Appends your chat messages",
        category = Module.Category.Chat
)
public final class ChatSuffix extends Module {

    private final String SUFFIX = " \u00bb \uff42\uff4c\uff4d";

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChat(PlayerChatEvent event) {
        if (shouldAppend(event.getMessage())) {
            event.setMessage(event.getMessage() + SUFFIX);
        }
    }

    private final Pattern pattern = Pattern.compile("[a-zA-Z&&>]");

    private boolean shouldAppend(String message) {
        return !pattern.matcher(message.substring(0, 1)).matches();
    }
}
