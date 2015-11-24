package org.fruttech.rendering;

import com.google.inject.name.Names;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class PropertiesModule extends BaseModule {
    public static final String CONFIG_PROPERTIES_FILE = "config.properties";

    @Override protected void configure() {
        Properties rawProperties = loadPropertiesFile(CONFIG_PROPERTIES_FILE);
        Names.bindProperties(binder(), rawProperties);
        bind(ApplicationContext.class).toProvider(ApplicationContext::getInstance);

        PropertyConfigurator.configure(loadPropertiesFile("log4j.properties"));

    }
}
