package org.fruttech.rendering.services;

import com.google.inject.Singleton;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SetConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RenderState;
import org.fruttech.rendering.data.SceneInfo;

@Singleton
public class HazelcastService implements RunnableService {
    public static final String RENDER_STATE_MAP = "render.state";
    public static final String RENDER_SCENES_SET = "renderer.scenes";
    private HazelcastInstance hazelcastInstance;

    public HazelcastService() {}

    public HazelcastInstance instance() {
        return hazelcastInstance;
    }

    public IMap<RNode, RenderState> getRenderState() {
        return hazelcastInstance.getMap(RENDER_STATE_MAP);
    }

    public ISet<SceneInfo> getRenderScenes() {
        return hazelcastInstance.getSet(RENDER_SCENES_SET);
    }

    @Override public void run() {
        final Config hzConfig = new Config();

        final MapConfig renderStatHzMapConfig = new MapConfig(RENDER_STATE_MAP);
        hzConfig.addMapConfig(renderStatHzMapConfig);

        final SetConfig scenesSetConfig = new SetConfig(RENDER_SCENES_SET);
        hzConfig.addSetConfig(scenesSetConfig);

        hazelcastInstance = Hazelcast.getOrCreateHazelcastInstance(hzConfig);
    }

    @Override public void stop() {
        hazelcastInstance.shutdown();
    }
}
