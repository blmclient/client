package me.gavin.blm.command.commands;

import me.gavin.blm.command.Command;
import me.gavin.blm.misc.Util;
import me.gavin.blm.module.Module;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public final class ModulesCmd extends Command {
    public ModulesCmd() {
        super("modules", "Shows all modules", "modules", "mods");
    }

    @Override
    public void execute(String[] args) {
        final StringBuilder sb = new StringBuilder("Modules: ");

        for (Module module : blm.getModuleManager().getModules()) {
            sb.append(module.isEnabled() ? GREEN : RED).append(module.getName()).append(", ");
        }

        Util.sendClientMessage(sb.substring(0, sb.toString().length() - 2));
    }
}
