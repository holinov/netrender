package org.fruttech.rendering.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RenderState;
import org.fruttech.rendering.data.SceneInfo;

@Singleton
public class RenderStateServiceImpl implements RenderStateService {
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
    }
}

