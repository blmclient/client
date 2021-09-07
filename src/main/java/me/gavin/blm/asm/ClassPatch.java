package me.gavin.blm.asm;

import org.objectweb.asm.Opcodes;

public abstract class ClassPatch implements Opcodes {

    private final String name;

    public ClassPatch(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
