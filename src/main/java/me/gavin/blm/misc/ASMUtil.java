package me.gavin.blm.misc;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

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
                }
            }
        }
        return list.get(ordinal);
    }

    public static MethodInsnNode findMethodInsn(MethodNode methodNode, int opcode, String owner, String name, String desc, int ordinal) {
        final List<MethodInsnNode> list = new ArrayList<>();
        for (AbstractInsnNode insn : methodNode.instructions.toArray()) {
            if (insn instanceof MethodInsnNode) {
                final MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.getOpcode() == opcode && methodInsn.owner.equals(owner) && methodInsn.name.equals(name) && methodInsn.desc.equals(desc)) {
                    list.add(methodInsn);
                }
            }
        }

        return list.get(ordinal);
    }

    public static String insnToString(AbstractInsnNode insn, MethodNode methodNode) {
        return insn.getOpcode() + " " + insn.getClass().getName() + " " + methodNode.instructions.indexOf(insn);
    }

    public static InsnNode findInsn(MethodNode methodNode, int opcode, int ordinal) {
        final List<InsnNode> list = new ArrayList<>();
        for (AbstractInsnNode insn : methodNode.instructions.toArray()) {
            if (insn instanceof InsnNode) {
                if (insn.getOpcode() == opcode)
                    list.add((InsnNode)insn);
            }
        }

        return list.get(ordinal);
    }

    public static AbstractInsnNode findBottom(MethodNode method) {
        return method.instructions.get(method.instructions.size() - 2);
    }
}