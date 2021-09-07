package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.*;

public final class NetworkManagerPatch extends ClassPatch {
    public NetworkManagerPatch() {
        super("net.minecraft.network.NetworkManager", "eo");
    }

    @MethodPatch(
            name = "sendPacket",
            desc = "(Lnet/minecraft/network/Packet;)V",
            obfName = "a",
            obfDesc = "(Lfj;)V"
    )
    public void sendPacketPatch(MethodNode methodNode, boolean deobfuscated) {
        System.out.println("SNED PACKEIT PATCHHHHHHHHHHHHHHHH POOPY");
        // instruction list
        final InsnList insnList = new InsnList();
        // load packet onto stack
        insnList.add(new VarInsnNode(ALOAD, 1));
        // call hook method
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "sendPacketHook",
                deobfuscated ? "(Lnet/minecraft/network/Packet;)Z" : "(Lfj;)Z", false));
        // create jump label
        final LabelNode label = new LabelNode();
        // add ifeq for if event is cancelled
        insnList.add(new JumpInsnNode(IFEQ, label));
        // return
        insnList.add(new InsnNode(RETURN));
        // finish label
        insnList.add(label);
        // insert at head of method
        methodNode.instructions.insert(insnList);
    }
}
