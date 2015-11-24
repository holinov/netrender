package org.fruttech.rendering.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.google.inject.Injector;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.fruttech.rendering.ApplicationContext;
import org.fruttech.rendering.ApplicationModule;
import org.fruttech.rendering.KafkaModule;
import org.fruttech.rendering.PropertiesModule;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.fruttech.rendering.serialization.RenderingJobSerializer;
import storm.kafka.*;

import java.util.List;

/**
 * Distributed org.fruttech.rendering topology
 */
public class RenderingTopology {

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void main(String[] args) {

        final Injector injector = ApplicationContext
                .withModules(
                        new PropertiesModule(),
                        new ApplicationModule(),
                        new KafkaModule()
                ).getInjector();

        final RenderingTopologyConfig topologyConfig = injector.getInstance(RenderingTopologyConfig.class);

        final String topicName = RenderingTopologyConstants.RENDER_JOBS_QUEUE_NAME;
        final BrokerHosts hosts = new ZkHosts(topologyConfig.zkConnect);

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //Configure spouts
        topologyBuilder.setSpout(RenderingTopologyConstants.JOB_SPOUT_NAME,
                new JobSpout(new KafkaSpout(getKafkaSpoutConfig(topicName, hosts)), false),
                topologyConfig.spouts);

        //Configure preprocessor bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.PREPROCESSOR_NAME, new PreprocessorBolt(), topologyConfig.preprocessors)
                .shuffleGrouping(RenderingTopologyConstants.JOB_SPOUT_NAME);

        //Configure renderer bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.RENDERER_NAME, new RendererBolt(), topologyConfig.renderers)
                .shuffleGrouping(RenderingTopologyConstants.PREPROCESSOR_NAME);

        //Configure combiner bolts
        topologyBuilder.setBolt(RenderingTopologyConstants.COMBINER_NAME, new CombinerBolt(), topologyConfig.combiners)
                .fieldsGrouping(RenderingTopologyConstants.RENDERER_NAME, new Fields(RenderingTopologyConstants.KEY_FIELD_NAME));

        Config conf = new Config();
        conf.setDebug(false);


        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(RenderingTopologyConstants.TOPOLOGY_NAME, conf, topologyBuilder.createTopology());


    }

    private static SpoutConfig getKafkaSpoutConfig(String topicName, BrokerHosts hosts) {
        SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/" + topicName, topicName + "-consumer-1");
        spoutConfig.scheme = new KeyValueSchemeAsMultiScheme(new KeyValueScheme() {
            @Override public List<Object> deserialize(byte[] ser) {
                return null;
            }

            @Override public Fields getOutputFields() {
                return new Fields(RenderingTopologyConstants.KEY_FIELD_NAME, RenderingTopologyConstants.DATA_FIELD_NAME);
            }

            @Override public List<Object> deserializeKeyAndValue(byte[] key, byte[] value) {
                final StringDeserializer keyDes = new StringDeserializer();
                final RenderingJobSerializer dataDes = new RenderingJobSerializer();

                final String keyVal = keyDes.deserialize(null, key);

                if (value.length <= 4) return new Values(key, new RenderingJob("error", -1, -1));

                final RenderingJob job = dataDes.deserialize(null, value);
                return new Values(keyVal, job);
            }
        });
        return spoutConfig;
    }
}