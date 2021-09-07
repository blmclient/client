package me.gavin.blm.command.commands;

import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;

public final class ToggleCmd extends Command {
    public ToggleCmd() {
        super("toggle", "Toggles a specified module", "toggle [module name]", "t");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Util.sendClientMessage(getArgSyntax());
            return;
        }

        final Module module = blm.getModuleManager().getModule(args[0]);

        if (module != null) {
            module.toggle();
            Util.sendClientMessage("Toggled " + module.getName());
        } else {
            Util.sendClientMessage("Unable to find the specified module");
        }
    }
}
