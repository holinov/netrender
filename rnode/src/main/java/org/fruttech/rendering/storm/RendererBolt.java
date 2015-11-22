package org.fruttech.rendering.storm;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.fruttech.rendering.data.jobs.RenderingJobPart;
import org.fruttech.rendering.data.jobs.RenderingJobPartResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RendererBolt extends ContextBolt{
    private static final Logger logger = LoggerFactory.getLogger(RendererBolt.class);

    @Override public void execute(Tuple input) {
        final String key = input.getString(0);
        final RenderingJobPart renderingJobPart = RenderingJobPart.fromJson(input.getString(1));
        try {
            //logger.info("Processing job part: " + renderingJobPart+" key: "+key);
            Thread.sleep(300);

            byte[] payload = new byte[1024 * 10];

            final RenderingJobPartResult result = new RenderingJobPartResult(renderingJobPart.getScene(),
                    renderingJobPart.getFromFrame(), renderingJobPart.getToFrame(),
                    renderingJobPart.getBucketInfo(), renderingJobPart.getFrame(),
                    renderingJobPart.getTotalBuckets(), payload);

            //TODO: due to byte array payload change serialization
            getCollector().emit(new Values(key, RenderingJobPartResult.toJson(result)));
            getCollector().ack(input);

        } catch (InterruptedException e) {
            logger.info("Rendering interrupted. Failing tuple.");

            //Fail to allow storm to replay tuple on other executor
            getCollector().fail(input);
        }
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(RenderingTopologyConstants.KEY_FIELD_NAME,RenderingTopologyConstants.DATA_FIELD_NAME));
    }
}
