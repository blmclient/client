package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.*;

public final class BlockPatch extends ClassPatch {
    public BlockPatch() {
        super("net.minecraft.block.Block", "akf");
    }

    @MethodPatch(
            name = "isCollidable",
            desc = "()Z",
            obfName = "n",
            obfDesc = "()Z"
    )
    public void isCollidablePatch(MethodNode methodNode, boolean deobfuscated) {
        // make instruction list
        final InsnList insnList = new InsnList();
        // load "this" (a0) onto stack for event
        insnList.add(new VarInsnNode(ALOAD, 0));
        // call hook method
        final String hookDesc = deobfuscated ? "(Lnet/minecraft/block/Block;)Z" : "(Lakf;)Z";
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "isCollidableHook", hookDesc, false));
        // create jump label
        final LabelNode jump = new LabelNode();
        // add ifeq jump for when event is cancelled
        insnList.add(new JumpInsnNode(IFEQ, jump));
        // push 0 onto stack (false)
        insnList.add(new InsnNode(ICONST_0));
        // return false
        insnList.add(new InsnNode(IRETURN));
        // finish label
        insnList.add(jump);
        // insert instructions into method head
        methodNode.instructions.insert(insnList);
    }
}
