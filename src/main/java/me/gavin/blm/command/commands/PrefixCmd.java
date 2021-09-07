package me.gavin.blm.command.commands;

import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;

public final class PrefixCmd extends Command {
    public PrefixCmd() {
        super("prefix", "Set the command prefix", "prefix [character]", "pfx", "p");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            Util.sendClientMessage(getArgSyntax());
            return;
        }

        blm.getCommandManager().prefix = args[0].toLowerCase();
        Util.sendClientMessage("Set the command prefix to " + args[0].toLowerCase());
    }
}
