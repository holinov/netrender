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
    boolean fired = false;

    @Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("job"));
    }

    @Override public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override public void nextTuple() {
        if(!fired) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final RenderingJob renderingJob = new RenderingJob("scene-1", 10, 15);
            collector.emit(new Values(RenderingJob.toJson(renderingJob)));
            fired = true;
        }
    }
}
