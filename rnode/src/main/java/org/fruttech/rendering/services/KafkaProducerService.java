package org.fruttech.rendering.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.data.jobs.RenderingJobPart;
import org.fruttech.rendering.serialization.RenderingJobPartSerializer;
import org.fruttech.rendering.serialization.RenderingJobSerializer;
import org.fruttech.rendering.storm.RenderingTopologyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;

@Singleton
public class KafkaProducerService implements RunnableService {
    public static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    @Inject KafkaServiceConfig kafkaServiceConfig;
    private KafkaProducer<String, RenderingJob> jobProducer;
    private KafkaProducer<String, RenderingJobPart> jobPartProducer;

    @Override public void run() {
        jobProducer = new KafkaProducer<>(getJobProducerProperties(), new StringSerializer(), new RenderingJobSerializer());
        jobPartProducer = new KafkaProducer<>(getJobPartProducerProperties(), new StringSerializer(), new RenderingJobPartSerializer());
    }

    @Override public void stop() {
        jobProducer.close();
        jobPartProducer.close();
    }

    private Properties getJobProducerProperties() {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServiceConfig.kafkaBrokers);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.fruttech.rendering.serialization.RenderingJobSerializer");

        return props;
    }

    private Properties getJobPartProducerProperties() {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServiceConfig.kafkaBrokers);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.fruttech.rendering.serialization.RenderingJobPartSerializer");

        return props;
    }

    public void sendJob(RenderingJob job) {
        final String jobId = UUID.randomUUID().toString();
        job.setId(jobId);

        final int partition = jobId.hashCode() % kafkaServiceConfig.topicPartitions;
        final ProducerRecord<String, RenderingJob> record =
                new ProducerRecord<>(RenderingTopologyConstants.RENDER_JOBS_QUEUE_NAME, partition, jobId, job);

        jobProducer.send(record,
                (metadata, exception) -> {
                    if (exception != null) {
                        logger.error("Error sending rendering job to kafka", exception);
                    } else {
                        logger.debug(String.format("Sent message to topic: %s partition: %d offset: %d",
                                metadata.topic(), metadata.partition(), metadata.offset()));
                    }
                });
    }

    /*public void sendJobPart(RenderingJobPart jobPart) {
        final String jobPartId = UUID.randomUUID().toString();
        jobPart.setJobPartId(jobPartId);
        jobPartProducer.send(new ProducerRecord<>(RenderingTopologyConstants.RENDER_JOB_PARTS_QUEUE_NAME, jobPartId, jobPart));
    }*/
}

