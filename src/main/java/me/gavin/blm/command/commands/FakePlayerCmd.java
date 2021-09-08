package me.gavin.blm.command.commands;

import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;

public final class FakePlayerCmd extends Command {
    public FakePlayerCmd() {
        super("fakeplayer", "Spawns a fake player entity", "fakeplayer [spawn|delete] [name]");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            Util.sendClientMessage(getArgSyntax());
            return;
        }

        
    }
}
