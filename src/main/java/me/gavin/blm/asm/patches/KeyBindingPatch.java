package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class KeyBindingPatch extends ClassPatch {
    public KeyBindingPatch() {
        super("net.minecraft.client.settings.KeyBinding", "bcu");
    }

    @MethodPatch(
            name = "isKeyDown",
            desc = "()Z",
            obfName = "",
            obfDesc = "()Z"
    )
    public void isKeyDownPatch(MethodNode methodNode, boolean deobfuscated) {
        AbstractInsnNode iteratorNode = methodNode.instructions.get(4); // ifeq
        // remove everything after the ifeq until ireturn
        while (iteratorNode.getOpcode() != IRETURN) {
            iteratorNode = iteratorNode.getNext();
            methodNode.instructions.remove(iteratorNode.getPrevious());
        }
    }
}
