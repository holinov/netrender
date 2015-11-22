package org.fruttech.rendering.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;

import java.util.Map;

public abstract class ContextSpout extends BaseRichSpout {

    public Map getConf() {
        return conf;
    }

    public TopologyContext getContext() {
        return context;
    }

    public SpoutOutputCollector getCollector() {
        return collector;
    }

    private Map conf;
    private TopologyContext context;
    private SpoutOutputCollector collector;

    @Override public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.conf = conf;
        this.context = context;
        this.collector = collector;
    }
}
