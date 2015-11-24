package org.fruttech.rendering.storm;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import com.google.inject.Inject;
import org.fruttech.rendering.data.SceneInfo;
import org.fruttech.rendering.data.jobs.RenderingJobPartResult;
import org.fruttech.rendering.services.RenderStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CombinerBolt extends ContextBolt {
    private static final Logger logger = LoggerFactory.getLogger(CombinerBolt.class);
    private final Map<String, List<RenderingJobPartResult>> results = new HashMap<>();

    @Inject RenderStateService renderStateService;

    @Override public void execute(Tuple input) {
        final String key = input.getString(0);
        final RenderingJobPartResult renderingJobPart = (RenderingJobPartResult) input.getValue(1);

        if (!results.containsKey(key)) {
            results.put(key, new LinkedList<>());
        }
        final List<RenderingJobPartResult> resultsForKey = results.get(key);
        resultsForKey.add(renderingJobPart);

        logger.info(String.format("Got job part result for key: %s. Have %d of %d buckets", key, resultsForKey.size(), renderingJobPart.getTotalBuckets()));
        getCollector().ack(input);

        if (renderingJobPart.getTotalBuckets() == resultsForKey.size()) {
            logger.info("All buckets gathered. Start assemble. Key " + key);
            results.remove(key);

            renderStateService.finishScene(new SceneInfo(renderingJobPart.getScene(), null, 0));
        }
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //Final bolt. No output fields
    }
}
