package org.fruttech.rendering.services;

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

    /**
     * Finish scene and remove info from state cache
     */
    void finishScene(SceneInfo scene);
}


