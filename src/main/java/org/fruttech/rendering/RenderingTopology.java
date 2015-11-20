package org.fruttech.rendering;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import com.google.inject.Injector;

import java.io.IOException;

/**
 * Distributed rendering topology
 */
public class RenderingTopology {
    public static void main(String[] args) {

        final Injector injector = ApplicationContext.getInstance().getInjector();
        final RenderingTopologyConfig topologyConfig = injector.getInstance(RenderingTopologyConfig.class);

        TopologyBuilder topologyBuilder = new TopologyBuilder();

        //Configure spouts
        topologyBuilder.setSpout(RenderingTopologyConstants.SPOUT_NAME, new RenderingTopologySpout(), topologyConfig.spouts);

        //Configure preprocessor bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.PREPROCESSOR_NAME, new PreprocessorBolt(), topologyConfig.preprocessors)
                .shuffleGrouping(RenderingTopologyConstants.SPOUT_NAME);

        //Configure renderer bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.RENDERER_NAME, new RendererBolt(), topologyConfig.renderers)
                .shuffleGrouping(RenderingTopologyConstants.PREPROCESSOR_NAME);

        //Configure combiner bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.COMBINER_NAME, new CombinerBolt(), topologyConfig.combiners)
                .partialKeyGrouping(RenderingTopologyConstants.RENDERER_NAME,new Fields("key"));


        Config conf = new Config();
        conf.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(RenderingTopologyConstants.TOPOLOGY_NAME, conf, topologyBuilder.createTopology());

        //Utils.sleep(10000);
        try {
            final int read = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cluster.killTopology("test");
        cluster.shutdown();
    }
}


