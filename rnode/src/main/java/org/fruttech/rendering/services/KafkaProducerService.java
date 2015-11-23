package org.fruttech.rendering.services;

import com.google.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.data.jobs.RenderingJobPart;

import java.util.Properties;
import java.util.UUID;

@Singleton
public class KafkaProducerService implements RunnableService {

    public static final String RENDER_JOBS_QUEUE_NAME = "RenderJobs";
    private static final String RENDER_JOB_PARTS_QUEUE_NAME = "RenderJobParts";
    private KafkaProducer<String, RenderingJob> jobProducer;
    private KafkaProducer<String, RenderingJobPart> jobPartProducer;

    @Override public void run() {
        jobProducer = new KafkaProducer<>(getJobProducerProperties());
        jobPartProducer = new KafkaProducer<>(getJobPartProducerProperties());

    }

    @Override public void stop() {
        jobProducer.close();
        jobPartProducer.close();
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

    private Properties getJobProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("partition.assignment.strategy", "range");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.fruttech.rendering.services.RenderingJobSerializer");

        return props;
    }

    private Properties getJobPartProducerProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("partition.assignment.strategy", "range");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.fruttech.rendering.services.RenderingJobPartSerializer");

        return props;
    }

    public void sendJob(RenderingJob job) {
        final String jobId = UUID.randomUUID().toString();
        job.setId(jobId);
        jobProducer.send(new ProducerRecord<>(RENDER_JOBS_QUEUE_NAME, jobId, job));
    }

    public void sendJobPart(RenderingJobPart jobPart) {
        final String jobPartId = UUID.randomUUID().toString();
        jobPart.setJobPartId(jobPartId);
        jobPartProducer.send(new ProducerRecord<>(RENDER_JOB_PARTS_QUEUE_NAME, jobPartId, jobPart));
    }
}

