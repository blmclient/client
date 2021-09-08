package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.*;

public final class EntityPlayerPatch extends ClassPatch {
    public EntityPlayerPatch() {
        super("net.minecraft.entity.player.EntityPlayer", "zs");
    }

    @MethodPatch(
            name = "isPushedByWater",
            desc = "()Z",
            obfName = "bg",
            obfDesc = "()Z"
    )
    public void isPushedByWaterPatch(MethodNode methodNode, boolean deobfuscated) {
        // make instruction list
        final InsnList insnList = new InsnList();
        // load "this" a0 onto stack
        insnList.add(new VarInsnNode(ALOAD, 0));
        // get entityId I field
        final String fieldOwner = deobfuscated ? "net/minecraft/entity/player/EntityPlayer" : "zs";
        final String fieldName = deobfuscated ? "entityId" : "g";
        insnList.add(new FieldInsnNode(GETFIELD, fieldOwner, fieldName, "I"));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "isPushedByWaterHook", "(I)Z", false));
        // create jump label for when event is cancelled
        final LabelNode jump = new LabelNode();
        // add ifeq jump
        insnList.add(new JumpInsnNode(IFEQ, jump));
        // push 0 (false) onto stack
        insnList.add(new InsnNode(ICONST_0));
        // return
        insnList.add(new InsnNode(IRETURN));
        // jump out of label
        insnList.add(jump);
        // insert at head of method
        methodNode.instructions.insert(insnList);
    }
}
