package org.fruttech.rendering.services;

import com.google.inject.Singleton;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.data.jobs.RenderingJobPart;

import java.util.HashMap;
import java.util.Properties;

@Singleton
public class KafkaConsumerService implements RunnableService {
    public KafkaConsumer<String, RenderingJob> getJobConsumer() {
        return jobConsumer;
    }

    public KafkaConsumer<String, RenderingJobPart> getJobPartConsumer() {
        return jobPartConsumer;
    }

    private KafkaConsumer<String,RenderingJob> jobConsumer;
    private KafkaConsumer<String, RenderingJobPart> jobPartConsumer;

    @Override public void run() {
        jobConsumer = new KafkaConsumer<>(getProperties());
        jobPartConsumer = new KafkaConsumer<>(getProperties());

    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("group.id", "test");
        props.put("session.timeout.ms", "1000");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "10000");
        return props;
    }

    @Override public void stop() {
        jobConsumer.close();
        jobPartConsumer.close();
    }
}
