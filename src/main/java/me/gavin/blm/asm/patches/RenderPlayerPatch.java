package me.gavin.blm.asm.patches;

import me.gavin.blm.asm.ClassPatch;
import me.gavin.blm.asm.MethodPatch;
import me.gavin.blm.misc.ASMHooks;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class RenderPlayerPatch extends ClassPatch {
    public RenderPlayerPatch() {
        super("net.minecraft.client.renderer.entity.RenderPlayer", "bvh");
    }

    @MethodPatch(
            name = "renderEntityName",
            desc = "(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V",
            obfName = "a",
            obfDesc = "(Lbnk;DDDLjava/lang/String;D)V"
    )
    public void renderEntityNamePatch(MethodNode methodNode, boolean deobfuscated) {
        // create instruction list
        final InsnList insnList = new InsnList();
        // load variables onto stack

        // AbstractClientPlayer
        insnList.add(new VarInsnNode(ALOAD, 1));
        // x
        insnList.add(new VarInsnNode(DLOAD, 2));
        // y
        insnList.add(new VarInsnNode(DLOAD, 3));
        // z
        insnList.add(new VarInsnNode(DLOAD, 4));
        // String name
        insnList.add(new VarInsnNode(ALOAD, 5));
        // distanceSq
        insnList.add(new VarInsnNode(DLOAD, 6));
        // add hook method insn
        final String desc = deobfuscated ? "(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)Z" : "(Lbnk;DDDLjava/lang/String;D)Z";
        insnList.add(new MethodInsnNode(INVOKESTATIC, ASMHooks.internalName, "renderEntityNameHook", desc, false));
        // insert at head of method
        methodNode.instructions.insert(insnList);
    }
}
