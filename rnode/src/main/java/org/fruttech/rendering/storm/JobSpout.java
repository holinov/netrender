package org.fruttech.rendering.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.kafka.KafkaSpout;

import java.util.Map;


public class JobSpout extends ContextSpout {
    private static final Logger logger = LoggerFactory.getLogger(JobSpout.class);
    private final KafkaSpout kafkaSpout;
    private boolean skipFails;

    public JobSpout(KafkaSpout kafkaSpout, boolean skipFails) {
        this.kafkaSpout = kafkaSpout;
        this.skipFails = skipFails;
    }

    @Override public void ack(Object msgId) {
        kafkaSpout.ack(msgId);
    }

    @Override public void activate() {
        kafkaSpout.activate();
    }

    @Override public void deactivate() {
        kafkaSpout.deactivate();
    }

    @Override public Map<String, Object> getComponentConfiguration() {
        return kafkaSpout.getComponentConfiguration();
    }

    @Override public void fail(Object msgId) {
        if (skipFails) kafkaSpout.ack(msgId);
        else kafkaSpout.fail(msgId);
    }

    @Override public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        kafkaSpout.open(conf, context, collector);
        super.open(conf, context, collector);
        logger.info("Opened JobSpout");
    }

    @Override public void close() {
        kafkaSpout.close();
        super.close();
        logger.info("Closed JobSpout");
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        kafkaSpout.declareOutputFields(declarer);
    }

    @Override public void nextTuple() {
        kafkaSpout.nextTuple();
    }
}
