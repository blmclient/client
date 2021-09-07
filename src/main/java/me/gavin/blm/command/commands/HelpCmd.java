package me.gavin.blm.command.commands;

import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;

public final class HelpCmd extends Command {
    public HelpCmd() {
        super("help", "Shows this message", "help|?", "?");
    }

    @Override
    public void execute(String[] args) {
        Util.sendClientMessage("Commands: ");
        for (Command command : blm.getCommandManager().getCommands()) {
            Util.sendClientMessage(blm.getCommandManager().prefix + command.getName() + ": " + command.getDescription());
        }
    }
}
