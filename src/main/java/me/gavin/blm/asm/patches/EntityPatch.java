package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.*;

public final class EntityPatch extends ClassPatch {

    public EntityPatch() {
        super("net.minecraft.entity.Entity", "rw");
    }

    @MethodPatch(
            name = "applyEntityCollision",
            desc = "(Lnet/minecraft/entity/Entity;)V",
            obfName = "i",
            obfDesc = "(Lrw;)V"
    )
    public void applyEntityCollisionPatch(MethodNode methodNode, boolean deobfuscated) {
        // create instruction list
        final InsnList insnList = new InsnList();
        // load entity onto stack (a1)
        insnList.add(new VarInsnNode(ALOAD, 1));
        // call hook function
        final String hookDesc = deobfuscated ? "(Lnet/minecraft/entity/Entity;)Z" : "(Lrw;)Z";
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "applyEntityCollisionHook", hookDesc, false));
        // create jump label
        final LabelNode jump = new LabelNode();
        // ifeq for when event is cancelled
        insnList.add(new JumpInsnNode(IFEQ, jump));
        // return if true
        insnList.add(new InsnNode(RETURN));
        // jump out of label
        insnList.add(jump);
        // insert instructions in method head
        methodNode.instructions.insert(insnList);
    }
}
