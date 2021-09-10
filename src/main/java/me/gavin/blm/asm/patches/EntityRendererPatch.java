package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import me.gavin.blm.misc.ASMUtil;
import org.objectweb.asm.tree.*;

public final class EntityRendererPatch extends ClassPatch {
    public EntityRendererPatch() {
        super("net.minecraft.client.renderer.EntityRenderer", "bnz");
    }

    // this patch is to modify the variable where they use the gammaSetting variable to control the lightmap brightness
    @MethodPatch(
            name = "updateLightmap",
            desc = "(F)V",
            obfName = "g",
            obfDesc = "(F)V"
    )
    public void updateLightmapPatch(MethodNode methodNode, boolean deobfuscated) {
        // make instruction list
        final InsnList insnList = new InsnList();
        // load target var onto stack
        insnList.add(new VarInsnNode(FLOAD, 15));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "updateLightmapHook", "(F)F", false));
        // store result of hook method into f15
        insnList.add(new VarInsnNode(FSTORE, 15));
        // find target instruction
        final VarInsnNode targetNode = ASMUtil.findVarInsn(methodNode, FSTORE, 15, 2);
        // insert insn list after
        methodNode.instructions.insertBefore(targetNode.getNext(), insnList);
    }

    @MethodPatch(
            name = "hurtCameraEffect",
            desc = "(F)V",
            obfName = "d",
            obfDesc = "(F)V"
    )
    public void hurtCameraEffectPatch(MethodNode methodNode, boolean deobfuscated) {
        // make instruction list
        final InsnList insnList = new InsnList();
        // load partialTicks (f1) onto stack
        insnList.add(new VarInsnNode(FLOAD, 1));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "hurtCameraEffectHook", "(F)Z", false));
        // create jump label
        final LabelNode jump = new LabelNode();
        // add if equals (for event cancellation)
        insnList.add(new JumpInsnNode(IFEQ, jump));
        // return if true
        insnList.add(new InsnNode(RETURN));
        // finish if statement (jump)
        insnList.add(jump);
        // insert at head of method
        methodNode.instructions.insert(insnList);
    }

    @MethodPatch(
            name = "setupFog",
            desc = "(IF)V",
            obfName = "a",
            obfDesc = "(IF)V"
    )
    public void setupFogPatch(MethodNode methodNode, boolean deobfuscated) {
        final InsnList insnList = new InsnList();
        insnList.add(new VarInsnNode(FLOAD, 2)); // partialTicks
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "setupFogHook", "(F)V", false)); // hook function
        // insert at bottom
        methodNode.instructions.insertBefore(ASMUtil.findBottom(methodNode), insnList);
    }
}