package me.gavin.blm.asm;

import me.gavin.blm.misc.ASMUtil;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ASMClassTransformer implements IClassTransformer {

    public static PatchManager patchManager = new PatchManager();

    private final Logger logger;

    public ASMClassTransformer() {
        logger = LogManager.getLogger("BLM Client ASM");
        logger.info("ASM service initialized");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        final ClassPatch patch = patchManager.getPatch(name);

        if (patch == null)
            return basicClass;

        logger.info("Patching " + name + (PatchManager.deobfuscated ? "" : " (" + transformedName + ")"));

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

                            if (methodNode.name.equals(patchName) && methodNode.desc.equals(patchDesc)) {
                                method.invoke(patch, methodNode, PatchManager.deobfuscated);
                                logger.info("Transformed: " + classNode.name + "." + methodNode.name + methodNode.desc);
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
