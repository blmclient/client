package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import me.gavin.blm.misc.ASMUtil;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

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
    private void updateLightmapPatch(MethodNode methodNode, boolean deobfuscated) {
        // make instruction list
        final InsnList insnList = new InsnList();
        // load target var onto stack
        insnList.add(new VarInsnNode(FLOAD, 15));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "updateLightmapHook", "(F)F", false));
        // store result of hook method into f15
        insnList.add(new VarInsnNode(FSTORE, 15));
        // find target instruction
        final VarInsnNode targetNode = ASMUtil.findVarInsn(methodNode, FSTORE, 15, 0);
        // insert insn list after
        methodNode.instructions.insertBefore(targetNode.getNext(), insnList);
    }
}