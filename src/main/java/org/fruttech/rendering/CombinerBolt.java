package org.fruttech.rendering;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import org.fruttech.rendering.data.RenderingJobPartResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CombinerBolt extends ContextBolt{
    private static final Logger logger = LoggerFactory.getLogger(CombinerBolt.class);
    private final Map<String,List<RenderingJobPartResult>> results = new HashMap<>();

    @Override public void execute(Tuple input) {
        final String key = input.getString(0);
        final RenderingJobPartResult renderingJobPart = RenderingJobPartResult.fromJson(input.getString(1));

        if(!results.containsKey(key)){
            results.put(key,new LinkedList<>());
        }
        final List<RenderingJobPartResult> resultsForKey = results.get(key);
        logger.debug(String.format("Got job part result for key: %s. Have %d of %d buckets", key, resultsForKey.size(), renderingJobPart.getTotalBuckets()));
        resultsForKey.add(renderingJobPart);
        getCollector().ack(input);

        if(renderingJobPart.getTotalBuckets() == resultsForKey.size()-1){
            logger.info("All buckets gathered. Start assemble");
        }
    }

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //Final bolt. No output fields
    }
}
