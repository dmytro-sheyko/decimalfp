package org.decimalfp.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.IdentityHashMap;
import java.util.Map;

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
        Map<ClassLoader, MutableMaybe> map = new IdentityHashMap<>();
        isTransformable(map, null);
        for (ClassLoader loader = ClassLoader.getSystemClassLoader(); loader != null; loader = loader.getParent()) {
            isTransformable(map, loader);
        }
        inst.addTransformer(new Transformer(map), true);
    }

    static boolean isTransformableBare(ClassLoader loader) {
        if (loader == null) {
            loader = BootstrapClassLoader.INSTANCE;
        }
        try {
            loader.loadClass(TransformerUtil.CLASS_DOT_FPUTIL);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    static boolean isTransformable(Map<ClassLoader, MutableMaybe> map, ClassLoader loader) {
        MutableMaybe result;
        synchronized (map) {
            result = map.get(loader);
            if (result == null) {
                result = new MutableMaybe();
                map.put(loader, result);
            }
        }
        boolean value;
        synchronized (result) {
            if (!result.ready_) {
                result.value_ = isTransformableBare(loader);
                result.ready_ = true;
            }
            value = result.value_;
        }
        return value;
    }

    static class MutableMaybe {
        boolean ready_ = false;
        boolean value_;
    }

    static class Transformer implements ClassFileTransformer {
        private final Map<ClassLoader, MutableMaybe> map_;

        public Transformer(Map<ClassLoader, MutableMaybe> map) {
            map_ = map;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (TransformerUtil.CLASS_DOT_FPUTIL.equals(className))
                return null;
            return isTransformable(map_, loader) ? 
                TransformerUtil.transform(classfileBuffer) :
                null;
        }
    }
}
