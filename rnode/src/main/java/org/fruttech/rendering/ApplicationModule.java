package org.fruttech.rendering;

import com.google.inject.name.Names;
import org.fruttech.rendering.services.*;

import java.util.Properties;

public class ApplicationModule extends BaseModule {
    public static final String CONFIG_PROPERTIES_FILE = "config.properties";

    @Override protected void configure() {

        Properties rawProperties = loadPropertiesFile(CONFIG_PROPERTIES_FILE);
        Names.bindProperties(binder(), rawProperties);

        bind(ApplicationContext.class).toProvider(ApplicationContext::getInstance);
        bind(RenderStateService.class).to(RenderStateServiceImpl.class);

        bindRunnableService(HazelcastService.class);
        bindRunnableService(KafkaConsumerService.class);
        bindRunnableService(KafkaProducerService.class);
    }

}
