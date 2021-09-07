package me.gavin.blm.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public final class PlayerChatEvent extends Event {

    private String message;
    private final String originalMessage;

    public PlayerChatEvent(String message) {
        this.message = this.originalMessage = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }
}
