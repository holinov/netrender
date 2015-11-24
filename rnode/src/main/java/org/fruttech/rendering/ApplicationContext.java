package org.fruttech.rendering;

import com.google.inject.*;
import org.fruttech.rendering.common.RunnableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
    private static ApplicationContext instance;
    private static List<Module> modules;
    private static boolean started;

    private final Injector injector;

    private ApplicationContext(Module... modules) {
        injector = Guice.createInjector(modules);
    }

    private ApplicationContext(List<Module> modules) {
        injector = Guice.createInjector(modules);
    }

    /**
     * Get ApplicationContext instance (if was all ready configured by {@link #withModules(AbstractModule...)}  })
     */
    public synchronized static ApplicationContext getInstance() {
        if (instance == null) {
            if (modules == null) throw new RuntimeException("Trying to access unconfigured instance is illegal");

            instance = new ApplicationContext(modules);
        }

        if (!started) {
            started = true;
            final Injector injector = instance.getInjector();
            final TypeLiteral<Set<RunnableService>> runnableServicesSetType = new TypeLiteral<Set<RunnableService>>() {};
            final Set<RunnableService> runnableServices = injector.getInstance(Key.get(runnableServicesSetType));
            start(runnableServices);
        }
        return instance;
    }

    private static void start(Set<RunnableService> runnableServices) {
        logger.info("=====> Starting Application Context");

        for (RunnableService service : runnableServices) {
            final String serviceName = service.getClass().getName();
            logger.info("=====> Starting service : " + serviceName);
            try {
                service.run();
                logger.info("=====> Started service : " + serviceName);
            } catch (Exception e) {
                logger.error("=====> Error starting service : " + serviceName, e);
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop(runnableServices);
        }));

        logger.info("=====> Application Context started");
    }

    private static void stop(Set<RunnableService> runnableServices) {
        logger.info("=====> Stopping Application Context");

        for (RunnableService service : runnableServices) {
            final String serviceName = service.getClass().getName();
            logger.info("=====> Stopping service : " + serviceName);
            try {
                service.stop();
                logger.info("=====> Stopped service : " + serviceName);
            } catch (Exception e) {
                logger.error("=====> Error stopping service : " + serviceName);
            }
        }

        logger.info("=====> Application Context stopped");
    }

    /**
     * Configure ApplicationContext with modules
     *
     * @param modules modules to be used in injector
     */
    public static ApplicationContext withModules(AbstractModule... modules) {
        if (instance != null)
            throw new RuntimeException("Trying to reconfigure all ready existing ApplicationContext is illegal");

        ApplicationContext.modules = new ArrayList<>(Arrays.asList(modules));
        return getInstance();
    }

    /**
     * Get current root injector
     */
    public Injector getInjector() {
        return injector;
    }
}

