package org.fruttech.rendering.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RenderState;
import org.fruttech.rendering.data.SceneInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class RenderStateServiceImpl implements RenderStateService {
    public static final Logger logger = LoggerFactory.getLogger(RenderStateService.class);

    @Inject HazelcastService instance;

    @Override public void setRenderState(RNode node, RenderState state) {
        final IMap<RNode, RenderState> map = instance.getRenderState();
        map.put(node, state);
    }

    @Override public RenderState getRenderState(RNode node) {
        final IMap<RNode, RenderState> map = instance.getRenderState();
        return map.get(node);
    }

    @Override public void addScene(SceneInfo scene) {
        final ISet<SceneInfo> scenes = instance.getRenderScenes();
        if (scenes.contains(scene)) scenes.remove(scene);
        scenes.add(scene);

        logger.info("Added scene " + scene);
    }

    @Override public void finishScene(SceneInfo scene) {
        final ISet<SceneInfo> scenes = instance.getRenderScenes();
        scenes.remove(scene);

        logger.info("Finished scene " + scene.getScene());
    }
}

