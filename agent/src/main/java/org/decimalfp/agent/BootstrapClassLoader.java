package org.decimalfp.agent;

/**
 * @author Dmytro.Sheyko
 */
class BootstrapClassLoader extends ClassLoader {
    static final BootstrapClassLoader INSTANCE = new BootstrapClassLoader();

    private BootstrapClassLoader() {
        super(null);
    }
}
