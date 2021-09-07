package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import me.gavin.blm.misc.ASMUtil;
import org.objectweb.asm.tree.*;

public final class EntityPlayerSPPatch extends ClassPatch {
    public EntityPlayerSPPatch() {
        super("net.minecraft.client.entity.EntityPlayerSP", "bnn");
    }

    @MethodPatch(
            name = "onLivingUpdate",
            desc = "()V",
            obfName = "n",
            obfDesc = "()V"
    )
    public void onLivingUpdatePatch(MethodNode methodNode, boolean deobfuscated) {
        // insert insn to call hook method at top of method for tick event
        methodNode.instructions.insert(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "onLivingUpdateHook", "()V", false));


        // inserting movement update event hook

        // create insn list
        final InsnList insnList = new InsnList();
        // load movementInput field onto stack
        final String owner = deobfuscated ? "net/minecraft/client/entity/EntityPlayerSP" : "bnn";
        final String name = deobfuscated ? "movementInput" : "e";
        final String desc = deobfuscated ? "Lnet/minecraft/util/MovementInput;" : "Lbnl;";
        // load self onto stack
        insnList.add(new VarInsnNode(ALOAD, 0));
        // get movementInput field
        insnList.add(new FieldInsnNode(GETFIELD, owner, name, desc));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "onLivingUpdateHook", "(" + desc + ")V", false));
        // find target insn node
        final String methodOwner = deobfuscated ? "net/minecraft/util/MovementInput" : "bnl";
        final String methodName = deobfuscated ? "updatePlayerMoveState" : "a";
        final MethodInsnNode targetMethodInsn = ASMUtil.findMethodInsn(methodNode, INVOKEVIRTUAL, methodOwner, methodName, "()V", 0);
        // insert after target insn
        methodNode.instructions.insertBefore(targetMethodInsn.getNext(), insnList);
    }
}
