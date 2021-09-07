package me.gavin.blm.asm;

import me.gavin.blm.misc.ASMUtil;
import net.minecraft.launchwrapper.IClassTransformer;
import org.lwjgl.Sys;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ASMClassTransformer implements IClassTransformer {

    public static PatchManager patchManager = new PatchManager();

    public ASMClassTransformer() {
        System.out.println("electric boogaloo");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        final ClassPatch patch = patchManager.getPatch(name);

        if (patch == null)
            return basicClass;

        System.out.println("Patching " + name);

        final ClassNode classNode = ASMUtil.toClassNode(basicClass);

        for (MethodNode methodNode : classNode.methods) {
            for (Method method : patch.getClass().getDeclaredMethods()) {
                if (!method.isAccessible())
                    method.setAccessible(true);
                if (method.isAnnotationPresent(MethodPatch.class)) {
                    if (method.getParameterCount() == 2 && method.getParameterTypes()[0] == MethodNode.class) {
                        try {
                            final MethodPatch methodPatch = method.getAnnotation(MethodPatch.class);
                            String patchName = methodPatch.obfName();
                            String patchDesc = methodPatch.obfDesc();
                            if (PatchManager.deobfuscated) {
                                patchName = methodPatch.name();
                                patchDesc = methodPatch.desc();
                            }
                            System.out.println(patchName + " " + methodNode.name);
                            if (methodNode.name.equals(patchName) && methodNode.desc.equals(patchDesc)) {
                                method.invoke(patch, methodNode, PatchManager.deobfuscated);
                                System.out.println("Transformed: " + classNode.name + "." + methodNode.name + methodNode.desc);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return ASMUtil.toBytes(classNode);
    }
}
