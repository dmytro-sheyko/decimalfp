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
    public static final String CLASS_DOT_FPUTIL = "org.decimalfp.core.FPUtil";
    public static final String CLASS_SLASH_FPUTIL = "org/decimalfp/core/FPUtil";
    public static final String CLASS_DESC_DECIMALFP = "Lorg/decimalfp/annotation/DecimalFP;";

    public static byte[] transform(byte[] classbytes) {
        ClassReader classreader = new ClassReader(classbytes);
        ClassWriter classwriter = new ClassWriter(classreader, 0);
        TransformerClassVisitor transformer = new TransformerClassVisitor(Opcodes.ASM5, classwriter);
        classreader.accept(transformer, 0);
        return transformer.isModified() ? classwriter.toByteArray() : null;
    }

    static class TransformerClassVisitor extends ClassVisitor {
        private boolean enabled_;
        private boolean changed_;

        public TransformerClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
            enabled_ = false;
            changed_ = false;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, boolean visible) {
            AnnotationVisitor visitor = super.visitAnnotation(name, visible);
            if (CLASS_DESC_DECIMALFP.equals(name)) {
                visitor = new TransformerAnnotationVisitor(api, visitor, this::setEnabled);
            }
            return visitor;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new TransformerMethodVisitor(api, super.visitMethod(access, name, desc, signature, exceptions), enabled_, this::setChanged);
        }

        void setEnabled(boolean enabled) {
            enabled_ = enabled;
        }

        void setChanged(boolean changed) {
            changed_ |= changed;
        }

        public boolean isModified() {
            return changed_;
        }
    }

    static class TransformerMethodVisitor extends MethodVisitor {
        private static final String DESC_DD_D = "(DD)D";
        private static final String DESC_FF_F = "(FF)F";
        private static final String DESC_F_D = "(F)D";
        private static final String DESC_D_F = "(D)F";
        private final BooleanConsumer setChanged_;
        private boolean enabled_;
        private boolean changed_;

        public TransformerMethodVisitor(int api, MethodVisitor mv, boolean enabled, BooleanConsumer setChanged) {
            super(api, mv);
            setChanged_ = setChanged;
            enabled_ = enabled;
            changed_ = false;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, boolean visible) {
            AnnotationVisitor visitor = super.visitAnnotation(name, visible);
            if (CLASS_DESC_DECIMALFP.equals(name)) {
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
                case Opcodes.DADD:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_dadd", DESC_DD_D, false);
                    return;
                case Opcodes.DSUB:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_dsub", DESC_DD_D, false);
                    return;
                case Opcodes.DMUL:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_dmul", DESC_DD_D, false);
                    return;
                case Opcodes.DDIV:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_ddiv", DESC_DD_D, false);
                    return;
                case Opcodes.DREM:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_drem", DESC_DD_D, false);
                    return;
                case Opcodes.FADD:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_fadd", DESC_FF_F, false);
                    return;
                case Opcodes.FSUB:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_fsub", DESC_FF_F, false);
                    return;
                case Opcodes.FMUL:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_fmul", DESC_FF_F, false);
                    return;
                case Opcodes.FDIV:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_fdiv", DESC_FF_F, false);
                    return;
                case Opcodes.FREM:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_frem", DESC_FF_F, false);
                    return;
                case Opcodes.F2D:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_f2d", DESC_F_D, false);
                    return;
                case Opcodes.D2F:
                    changed_ = true;
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_SLASH_FPUTIL, "dec_d2f", DESC_D_F, false);
                    return;
                }
            }
            super.visitInsn(opcode);
        }

        @Override
        public void visitEnd() {
            setChanged_.apply(changed_);
            super.visitEnd();
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
