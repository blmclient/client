package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.SpecialPatch;
import org.objectweb.asm.tree.ClassNode;

public class BlockPortalPatch extends ClassPatch {
    public BlockPortalPatch() {
        super("net.minecraft.block.BlockPortal", "any");
    }

    // for portals no hitbox feature
    @SpecialPatch
    public void blockPortalPatch(ClassNode classNode, boolean deobfuscated) {

    }
}