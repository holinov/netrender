package org.fruttech.rendering;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.fruttech.rendering.data.RenderingJob;
import org.fruttech.rendering.data.RenderingJobPart;

public class PreprocessorBolt extends ContextBolt{

    public static final int TOTAL_BUCKETS = 10;

    @Override public void execute(Tuple input) {
        final RenderingJob job = RenderingJob.fromJson(input.getString(0));
        for (long i = job.getFromFrame(); i <= job.getToFrame(); i++) {
            for (int j = 0; j < TOTAL_BUCKETS; j++) {
                final String frameKey = job.getScene()+"-"+j;
                final RenderingJobPart renderingJobPart = new RenderingJobPart(job.getScene(), job.getFromFrame(), job.getToFrame(), "bucket-" + j, i, TOTAL_BUCKETS);
                getCollector().emit(new Values(frameKey,RenderingJobPart.toJson(renderingJobPart)));
            }
        }
        getCollector().ack(input);
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(RenderingTopologyConstants.KEY_FIELD_NAME,RenderingTopologyConstants.DATA_FIELD_NAME));
    }
}