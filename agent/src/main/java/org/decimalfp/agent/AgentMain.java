package org.decimalfp.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.decimalfp.transform.TransformerUtil;

/**
 * @author Dmytro.Sheyko
 */
public class AgentMain {
    public static void premain(String args, Instrumentation inst) {
        main(inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(inst);
    }

    static void main(Instrumentation inst) {
        inst.addTransformer(new Transformer(), true);
    }

    static class Transformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            try {
                if (loader == null) {
                    return null;
                }
                loader.loadClass(TransformerUtil.CLASS_FPUTIL_DOT);
            } catch (Throwable e) {
                e.printStackTrace(System.out);
                return null;
            }
            return TransformerUtil.transform(classfileBuffer);
        }
    }
}
