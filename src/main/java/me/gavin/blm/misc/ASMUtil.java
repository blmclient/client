package me.gavin.blm.misc;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.ArrayList;
import java.util.List;

public final class ASMUtil {

    public static ClassNode toClassNode(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.EXPAND_FRAMES);
        return classNode;
    }

    public static byte[] toBytes(ClassNode classNode) {
        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    public static VarInsnNode findVarInsn(MethodNode methodNode, int opcode, int index, int ordinal) {
        final List<VarInsnNode> list = new ArrayList<>();
        for (AbstractInsnNode insn : methodNode.instructions.toArray()) {
            if (insn instanceof VarInsnNode) {
                final VarInsnNode varinsn = (VarInsnNode) insn;
                if (varinsn.getOpcode() == opcode && varinsn.var == index) {
                    list.add(varinsn);
                    System.out.println(insnToString(varinsn, methodNode));
                }
            }
        }
        return list.get(ordinal);
    }

    public static String insnToString(AbstractInsnNode insn, MethodNode methodNode) {
        return insn.getOpcode() + " " + insn.getClass().getName() + " " + methodNode.instructions.indexOf(insn);
    }
}