package org.decimalfp.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Dmytro.Sheyko
 */
public class TransformerUtil {
    public static byte[] transform(byte[] classbytes) {
        ClassReader classReader = new ClassReader(classbytes);
        ClassWriter classWriter = new ClassWriter(0);
        TransformerClassVisitor transformer = new TransformerClassVisitor(Opcodes.ASM5, classWriter);
        classReader.accept(transformer, 0);
        byte[] result = classWriter.toByteArray();
        return result;
    }

    static class TransformerClassVisitor extends ClassVisitor {
        public TransformerClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new TransformerMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
        }
    }

    static class TransformerMethodVisitor extends MethodVisitor {
        private static final String CLASS_UTIL = "org/decimalfp/core/FPUtil";
        private static final String DESC_DD_D = "(DD)D";
        private static final String DESC_FF_F = "(FF)F";

        public TransformerMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            switch (opcode) {
            default:
                super.visitInsn(opcode);
                break;
            case Opcodes.DADD:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_dadd", DESC_DD_D, false);
                break;
            case Opcodes.DSUB:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_dsub", DESC_DD_D, false);
                break;
            case Opcodes.DMUL:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_dmul", DESC_DD_D, false);
                break;
            case Opcodes.DDIV:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_ddiv", DESC_DD_D, false);
                break;
            case Opcodes.DREM:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_drem", DESC_DD_D, false);
                break;
            case Opcodes.FADD:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_fadd", DESC_FF_F, false);
                break;
            case Opcodes.FSUB:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_fsub", DESC_FF_F, false);
                break;
            case Opcodes.FMUL:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_fmul", DESC_FF_F, false);
                break;
            case Opcodes.FDIV:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_fdiv", DESC_FF_F, false);
                break;
            case Opcodes.FREM:
                super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_UTIL, "dec_frem", DESC_FF_F, false);
                break;
            }
        }
    }
}
