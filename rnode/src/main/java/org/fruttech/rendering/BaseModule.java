package org.fruttech.rendering;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.fruttech.rendering.common.RunnableService;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Base class for modules
 * Holds some convenience methods
 */
public abstract class BaseModule extends AbstractModule {
    private static Multibinder<RunnableService> serviceBinder;

    /**
     * Loads properties from a file
     */
    public static Properties loadPropertiesFile(String propertiesFileResourcePath) {
        Properties properties = new Properties();
        loadPropertiesFile(properties, propertiesFileResourcePath);
        return properties;
    }

    /**
     * Loads properties from a file and appends it to the provided properties file overwriting if some property already existed
     */
    @SuppressWarnings("ConstantConditions")
    public static void loadPropertiesFile(Properties properties, String propertiesFileResourcePath) {
        ClassLoader loader = PropertyLoader.class.getClassLoader();
        URL url = loader.getResource(propertiesFileResourcePath);
        try {
            properties.load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bindRunnableService(Class<? extends RunnableService> service) {
        getServiceBinder().addBinding().to(service);
    }

    public Multibinder<RunnableService> getServiceBinder() {
        if (serviceBinder == null) {
            serviceBinder = Multibinder.newSetBinder(binder(), RunnableService.class);
        }
        return serviceBinder;
    }
}
