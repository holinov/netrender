package org.fruttech.rendering;

import org.fruttech.rendering.services.KafkaProducerService;

public class KafkaModule extends BaseModule {
    @Override protected void configure() {
        bindRunnableService(KafkaProducerService.class);
    }
}
