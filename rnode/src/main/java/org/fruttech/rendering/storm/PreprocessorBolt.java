package org.fruttech.rendering.storm;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.inject.Inject;
import org.fruttech.rendering.data.SceneInfo;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.data.jobs.RenderingJobPart;
import org.fruttech.rendering.services.RenderStateService;

public class PreprocessorBolt extends ContextBolt {

    public static final int TOTAL_FRAME_BUCKETS = 2;
    @Inject RenderStateService renderStateService;

    @Override public void execute(Tuple input) {
        try {
            final RenderingJob job = (RenderingJob) input.getValue(1);

            long sceneBuckets = 0;
            for (long i = job.getFromFrame(); i <= job.getToFrame(); i++) {
                for (int j = 0; j < TOTAL_FRAME_BUCKETS; j++) {
                    final String frameKey = job.getScene() + "-" + i;
                    final RenderingJobPart renderingJobPart = new RenderingJobPart(job.getScene(), job.getFromFrame(), job.getToFrame(), "bucket-" + j, i, TOTAL_FRAME_BUCKETS);
                    getCollector().emit(new Values(frameKey, RenderingJobPart.toJson(renderingJobPart)));
                    sceneBuckets++;
                }
            }

            registerRenderingJob(job, sceneBuckets);

            getCollector().ack(input);
        } catch (Exception e) {
            getCollector().fail(input);
        }
    }

    private void registerRenderingJob(RenderingJob job, long buckets) {
        renderStateService.addScene(new SceneInfo(job.getScene(), job, buckets));
        //renderStateService.setRenderState(null, new RenderState());
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(RenderingTopologyConstants.KEY_FIELD_NAME, RenderingTopologyConstants.DATA_FIELD_NAME));
    }
}
