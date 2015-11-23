package org.fruttech.rendering.services;

import com.google.inject.Singleton;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.data.jobs.RenderingJobPart;

import java.util.Properties;

@Singleton
public class KafkaConsumerService implements RunnableService {
    private KafkaConsumer<String, RenderingJob> jobConsumer;
    private KafkaConsumer<String, RenderingJobPart> jobPartConsumer;

    public KafkaConsumer<String, RenderingJob> getJobConsumer() {
        return jobConsumer;
    }

    public KafkaConsumer<String, RenderingJobPart> getJobPartConsumer() {
        return jobPartConsumer;
    }

    @Override public void run() {
        jobConsumer = new KafkaConsumer<>(getJobConsumerProperties());
        jobPartConsumer = new KafkaConsumer<>(getJobPartConsumerProperties());
    }

    private Properties getJobConsumerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("partition.assignment.strategy", "range");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.fruttech.rendering.services.RenderingJobSerializer");

        return props;
    }

    private Properties getJobPartConsumerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("partition.assignment.strategy", "range");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.fruttech.rendering.services.RenderingJobPartSerializer");

        return props;
    }

    @Override public void stop() {
        jobConsumer.close();
        jobPartConsumer.close();
    }
}

