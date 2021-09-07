package me.gavin.blm.asm;

import org.objectweb.asm.Opcodes;

public abstract class ClassPatch implements Opcodes {

    private final String name, obfName;

    public ClassPatch(String name, String obfName) {
        this.name = name;
        this.obfName = obfName;
    }

    public String getName() {
        return name;
    }

    public String getObfName() {
        return obfName;
    }
}
