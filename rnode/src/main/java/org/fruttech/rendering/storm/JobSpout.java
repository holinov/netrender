package org.fruttech.rendering.storm;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.google.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.fruttech.rendering.data.SceneInfo;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.services.KafkaConsumerService;
import org.fruttech.rendering.services.RenderStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JobSpout extends ContextSpout {
    private static final Logger logger = LoggerFactory.getLogger(JobSpout.class);

    @Inject KafkaConsumerService kafkaConsumerService;
    @Inject RenderStateService renderStateService;

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(RenderingTopologyConstants.DATA_FIELD_NAME));
    }

    @Override public void nextTuple() {
        final KafkaConsumer<String, RenderingJob> jobConsumer = kafkaConsumerService.getJobConsumer();
        final Map<TopicPartition, Long> processed = process(jobConsumer.poll(100));
    }

    private Map<TopicPartition, Long> process(Map<String, ConsumerRecords<String, RenderingJob>> records) {
        Map<TopicPartition, Long> processedOffsets = new HashMap<>();
        for (Map.Entry<String, ConsumerRecords<String, RenderingJob>> recordMetadata : records.entrySet()) {
            List<ConsumerRecord<String, RenderingJob>> recordsPerTopic = recordMetadata.getValue().records();
            for (ConsumerRecord<String, RenderingJob> record : recordsPerTopic) {
                // process record
                try {
                    final RenderingJob job = record.value();

                    renderStateService.addScene(buildSceneInfo(job));

                    getCollector().emit(new Values(job));
                    processedOffsets.put(record.topicAndPartition(), record.offset());
                } catch (Exception e) {
                    logger.error("Error sending message to kafka", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return processedOffsets;
    }

    private SceneInfo buildSceneInfo(RenderingJob renderingJob) {
        return new SceneInfo(renderingJob.getScene(), renderingJob);
    }
}
