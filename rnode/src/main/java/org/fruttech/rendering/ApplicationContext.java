package org.fruttech.rendering;

import com.google.inject.*;

public class ApplicationContext{
    private static ApplicationContext instance;
    private final Injector injector;

    private ApplicationContext(Module... modules) {
        injector = Guice.createInjector(modules);
    }

    /**
     * Getter for property 'instance'.
     *
     * @return Value for property 'instance'.
     */
    public synchronized static ApplicationContext getInstance() {
        if (instance == null) instance = new ApplicationContext(new ApplicationModule());
        return instance;
    }

    /**
     * Getter for property 'injector'.
     *
     * @return Value for property 'injector'.
     */
    public Injector getInjector() {
        return injector;
    }
}

