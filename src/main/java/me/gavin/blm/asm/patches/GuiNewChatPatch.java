package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import me.gavin.blm.misc.ASMUtil;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class GuiNewChatPatch extends ClassPatch {
    public GuiNewChatPatch() {
        super("net.minecraft.client.gui.GuiNewChat", "bds");
    }

    @MethodPatch(
            name = "drawChat",
            desc = "(I)V",
            obfName = "a",
            obfDesc = "(I)V"
    )
    public void drawChatPatch(MethodNode methodNode, boolean deobfuscated) {
        // find target insn
        final String owner = deobfuscated ? "net/minecraft/client/gui/GuiNewChat" : "bds";
        final String name = deobfuscated ? "drawRect" : "a";
        final String desc = "(IIIII)V";
        final MethodInsnNode targetInsn = ASMUtil.findMethodInsn(methodNode, INVOKESTATIC, owner, name, desc, 0);
        // replace target insn with hook insn
        methodNode.instructions.set(targetInsn, new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "drawChatHook", "(IIIII)V", false));
    }
}
