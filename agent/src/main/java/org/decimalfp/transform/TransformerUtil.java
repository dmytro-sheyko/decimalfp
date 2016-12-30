package org.decimalfp.transform;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Dmytro.Sheyko
 */
public class TransformerUtil {
    public static final String CLASS_FPUTIL = "org/decimalfp/core/FPUtil";
    public static final String CLASS_ANN_FP = "org/decimalfp/core/DecimalFP";

    public static byte[] transform(byte[] classbytes) {
        ClassReader classReader = new ClassReader(classbytes);
        ClassWriter classWriter = new ClassWriter(0);
        TransformerClassVisitor transformer = new TransformerClassVisitor(Opcodes.ASM5, classWriter);
        classReader.accept(transformer, 0);
        byte[] result = classWriter.toByteArray();
        return result;
    }

    static class TransformerClassVisitor extends ClassVisitor {
        private boolean enabled_;

        public TransformerClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
            enabled_ = false;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, boolean visible) {
            AnnotationVisitor visitor = super.visitAnnotation(name, visible);
            if ("Lorg/decimalfp/annotation/DecimalFP;".equals(name)) {
                visitor = new TransformerAnnotationVisitor(api, visitor, this::setEnabled);
            }
            return visitor;
        }

        void setEnabled(boolean enabled) {
            enabled_ = enabled;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new TransformerMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions), enabled_);
        }
    }

    static class TransformerMethodVisitor extends MethodVisitor {
        private static final String DESC_DD_D = "(DD)D";
        private static final String DESC_FF_F = "(FF)F";
        private boolean enabled_;

        public TransformerMethodVisitor(int api, MethodVisitor mv, boolean enabled) {
            super(api, mv);
            enabled_ = enabled;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, boolean visible) {
            AnnotationVisitor visitor = super.visitAnnotation(name, visible);
            if ("Lorg/decimalfp/annotation/DecimalFP;".equals(name)) {
                visitor = new TransformerAnnotationVisitor(api, visitor, this::setEnabled);
            }
            return visitor;
        }

        void setEnabled(boolean enabled) {
            enabled_ = enabled;
        }

        @Override
        public void visitInsn(int opcode) {
            if (enabled_) {
                switch (opcode) {
                default:
                    super.visitInsn(opcode);
                    break;
                case Opcodes.DADD:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_dadd", DESC_DD_D, false);
                    break;
                case Opcodes.DSUB:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_dsub", DESC_DD_D, false);
                    break;
                case Opcodes.DMUL:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_dmul", DESC_DD_D, false);
                    break;
                case Opcodes.DDIV:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_ddiv", DESC_DD_D, false);
                    break;
                case Opcodes.DREM:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_drem", DESC_DD_D, false);
                    break;
                case Opcodes.FADD:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_fadd", DESC_FF_F, false);
                    break;
                case Opcodes.FSUB:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_fsub", DESC_FF_F, false);
                    break;
                case Opcodes.FMUL:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_fmul", DESC_FF_F, false);
                    break;
                case Opcodes.FDIV:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_fdiv", DESC_FF_F, false);
                    break;
                case Opcodes.FREM:
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_FPUTIL, "dec_frem", DESC_FF_F, false);
                    break;
                }
            } else {
                super.visitInsn(opcode);
            }
        }
    }

    static class TransformerAnnotationVisitor extends AnnotationVisitor {
        private final BooleanConsumer setEnabled_;
        private boolean enabled_;

        public TransformerAnnotationVisitor(int api, AnnotationVisitor av, BooleanConsumer setEnabled) {
            super(api, av);
            setEnabled_ = setEnabled;
            enabled_ = true;
        }

        @Override
        public void visit(String name, Object value) {
            if ("value".equals(name) && value instanceof Boolean) {
                enabled_ = ((Boolean) value).booleanValue();
            }
            super.visit(name, value);
        }

        @Override
        public void visitEnd() {
            setEnabled_.apply(enabled_);
            super.visitEnd();
        }
    }

    interface BooleanConsumer {
        void apply(boolean v);
    }
}
