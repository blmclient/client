package me.gavin.blm.command;

import me.gavin.blm.events.PlayerChatEvent;
import me.gavin.blm.misc.MC;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public final class CommandManager implements MC {

    public String prefix = ".";

    private final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        MinecraftForge.EVENT_BUS.register(this);

        // add commands

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.getOriginalMessage().startsWith(prefix)) {
            event.setCanceled(true);
            mc.ingameGUI.getChatGUI().addToSentMessages(event.getOriginalMessage());
            final String name = processName(event.getOriginalMessage());
            final String[] args = processArgs(event.getOriginalMessage());
            for (Command command : commands) {
                if (command.getName().equalsIgnoreCase(name) || command.getAliases().contains(name.toLowerCase())) {
                    command.execute(args);
                }
            }
        }
    }

    private String processName(String message) {
        return message.split(" ")[0].substring(prefix.length());
    }

    private String[] processArgs(String message) {
        final String[] split = message.split(" ");
        if (split.length == 1) {
            return new String[] {};
        } else {
            return Arrays.copyOfRange(split, 1, split.length);
        }
    }
}
