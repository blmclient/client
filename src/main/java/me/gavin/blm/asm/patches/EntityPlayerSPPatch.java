package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class EntityPlayerSPPatch extends ClassPatch {
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
        // insert insn to call hook method at top of method
        methodNode.instructions.insert(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "onUpdateHook", "()V", false));
    }
}
