package org.fruttech.rendering;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.fruttech.rendering.data.RenderingJob;

import java.util.Map;

public class RenderingTopologySpout extends BaseRichSpout {
    private SpoutOutputCollector collector;

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("job"));
    }

    @Override public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override public void nextTuple() {
        final RenderingJob renderingJob = new RenderingJob("scene-1",10,13);
        collector.emit(new Values(RenderingJob.toJson(renderingJob)));
    }
}
