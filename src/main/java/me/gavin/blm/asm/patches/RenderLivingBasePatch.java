package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import me.gavin.blm.misc.ASMUtil;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class RenderLivingBasePatch extends ClassPatch {
    public RenderLivingBasePatch() {
        super("net.minecraft.client.renderer.entity.RenderLivingBase", "bsy");
    }

    @MethodPatch(
            name = "renderModel",
            desc = "(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V",
            obfName = "a",
            obfDesc = "(Lsf;FFFFFF)V"
    )
    public void renderModelPatch(MethodNode methodNode, boolean deobfuscated) {
        // find target insn
        final String targetOwner = deobfuscated ? "net/minecraft/client/model/ModelBase" : "bju";
        final String targetName = deobfuscated ? "render" : "a";
        final String targetDesc = deobfuscated ? "(Lnet/minecraft/entity/Entity;FFFFFF)V" : "(Lrw;FFFFFF)V";

        final MethodInsnNode targetInsnNode = ASMUtil.findMethodInsn(methodNode, INVOKEVIRTUAL, targetOwner, targetName, targetDesc, 0);
        // replace target insn with hook method insn
        final String hookDesc = deobfuscated ? "(Lnet/minecraft/client/model/ModelBase;Lnet/minecraft/entity/Entity;FFFFFF)V" : "(Lbju;Lrw;FFFFFF)V";
        methodNode.instructions.set(targetInsnNode, new MethodInsnNode(
                INVOKESTATIC, ASMHooks.internalName, "renderHook", hookDesc, false));
    }
}