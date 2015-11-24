package org.fruttech.rendering.services;

import com.google.inject.Inject;

import javax.inject.Named;

public class KafkaServiceConfig {
    @Inject @Named("kafka.brokers")
    public String kafkaBrokers;

    @Inject @Named("kafka.topic.partitions")
    public int topicPartitions;
}
