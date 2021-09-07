package me.gavin.blm.command;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private final String name;
    private final String argSyntax;
    private final String description;
    private final List<String> aliases;

    public Command(String name, String description, String argSyntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.argSyntax = argSyntax;
        this.aliases = Arrays.asList(aliases);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getArgSyntax() {
        return argSyntax;
    }

    public abstract void execute(String[] args);
}
