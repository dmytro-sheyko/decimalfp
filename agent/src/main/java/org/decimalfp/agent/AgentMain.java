package org.decimalfp.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.decimalfp.transform.TransformerUtil;

/**
 * @author Dmytro.Sheyko
 */
public class AgentMain {
    static final AtomicLong TransformTime_ = new AtomicLong();
    static final AtomicInteger TransformCount_ = new AtomicInteger();

    public static void premain(String args, Instrumentation inst) {
        main(inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        main(inst);
    }

    static void onTransformFinished(long start, long end) {
        long time = end - start;
        TransformCount_.incrementAndGet();
        TransformTime_.addAndGet(time);
    }

    static void printStatistics() {
        System.out.println("Transform:\t" + TransformCount_.get() + "\t" + TransformTime_.get());
    }

    static void main(Instrumentation inst) {
        Runtime.getRuntime().addShutdownHook(new Thread(AgentMain::printStatistics, "decimalfp-printStatistics"));
        Map<ClassLoader, BoolResult> map = new WeakHashMap<>();
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

    static boolean isTransformable(Map<ClassLoader, BoolResult> map, ClassLoader loader) {
        BoolResult result;
        synchronized (map) {
            result = map.get(loader);
            if (result == null) {
                result = new BoolResult();
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

    static class BoolResult {
        boolean ready_ = false;
        boolean value_;
    }

    static class Transformer implements ClassFileTransformer {
        private final Map<ClassLoader, BoolResult> map_;

        public Transformer(Map<ClassLoader, BoolResult> map) {
            map_ = map;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            long start = System.nanoTime();
            try {
                if (TransformerUtil.CLASS_DOT_FPUTIL.equals(className))
                    return null;
                return isTransformable(map_, loader) ?
                        TransformerUtil.transform(classfileBuffer) :
                        null;
            } finally {
                onTransformFinished(start, System.nanoTime());
            }
        }
    }
}
