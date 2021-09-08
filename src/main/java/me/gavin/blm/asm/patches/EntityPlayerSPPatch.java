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

        // portalGui thing

        // find target method insn
        final String methodOwner2 = deobfuscated ? "net/minecraft/client/Minecraft" : "bcx";
        final String methodName2 = deobfuscated ? "displayGuiScreen" : "a";
        final String methodDesc2 = deobfuscated ? "(Lnet/minecraft/client/gui/GuiScreen;)V" : "(Lbft;)V";
        final MethodInsnNode targetMethodInsn2 = ASMUtil.findMethodInsn(methodNode, INVOKEVIRTUAL, methodOwner2, methodName2, methodDesc2, 0);
        // create hook insn
        final String hookDesc = deobfuscated ?
                "(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/GuiScreen;)V" :
                "(Lbcx;Lbft;)";
        final MethodInsnNode hookInsn = new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "onLivingUpdateHook", hookDesc, false);
        // replace target insn
        methodNode.instructions.set(targetMethodInsn2, hookInsn);
    }
}
