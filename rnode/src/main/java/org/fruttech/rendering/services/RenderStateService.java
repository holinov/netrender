package org.fruttech.rendering.services;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RenderState;
import org.fruttech.rendering.data.SceneInfo;

/**
 * Render stat service.
 * Used to store and monitor state of each Render Node
 */
public interface RenderStateService {
    /**
     * Set RNode sate
     */
    void setRenderState(RNode node, RenderState state);

    /**
     * Get state of RNode
     */
    RenderState getRenderState(RNode node);

    /**
     * Add queued scene info
     */
    void addScene(SceneInfo scene);
}


