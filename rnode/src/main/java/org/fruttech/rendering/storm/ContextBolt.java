package org.fruttech.rendering.storm;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichBolt;
import org.fruttech.rendering.ApplicationContext;

import java.util.Map;

public abstract class ContextBolt extends BaseRichBolt {
    private OutputCollector collector;

    /**
     * Getter for property 'collector'.
     *
     * @return Value for property 'collector'.
     */
    public OutputCollector getCollector() {
        return collector;
    }

    @Override public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        ApplicationContext.getInstance().getInjector().injectMembers(this);
        this.collector = collector;
    }
}
