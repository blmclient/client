package me.gavin.blm.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ASMClassTransformer implements IClassTransformer {

    public static PatchManager patchManager = new PatchManager();

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        final ClassPatch classPatch = patchManager.getPatch(transformedName);

        if (classPatch == null)
            return basicClass;

        final ClassNode classNode = ASMUtil.toClassNode(basicClass);

        for (MethodNode methodNode : classNode.methods) {
            System.out.println(methodNode.name);
        }

        return ASMUtil.toBytes(classNode);
    }
}
