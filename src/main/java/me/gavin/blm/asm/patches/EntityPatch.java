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
            name = "isPushedByWater",
            desc = "()Z",
            obfName = "bg",
            obfDesc = "()Z"
    )
    public void isPushedByWaterPatch(MethodNode methodNode, boolean deobfuscated) {
        // create instruction list
        final InsnList insnList = new InsnList();
        // load self onto stack
        insnList.add(new VarInsnNode(ALOAD, 0));
        // push Entity.entityId field onto stack
        final String fieldOwner = deobfuscated ? "net/minecraft/entity/Entity" : "rw";
        final String fieldName = deobfuscated ? "entityId" : "g";
        insnList.add(new FieldInsnNode(GETFIELD, fieldOwner, fieldName, "I"));
        // call hook function
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "isPushedByWaterHook", "(I)Z", false));
        // create jump label
        final LabelNode labelNode = new LabelNode();
        // add ifeq for when event is cancelled
        insnList.add(new JumpInsnNode(IFEQ, labelNode));
        // push 0 (false) onto stack
        insnList.add(new InsnNode(ICONST_0));
        // return
        insnList.add(new InsnNode(IRETURN));
        // finish label
        insnList.add(labelNode);
        // insert into head of method
        methodNode.instructions.insert(insnList);
    }
}
