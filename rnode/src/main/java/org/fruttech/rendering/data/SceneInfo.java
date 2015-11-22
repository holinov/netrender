package org.fruttech.rendering.data;

import org.fruttech.rendering.data.jobs.RenderingJob;

public class SceneInfo{
    public SceneInfo(String scene, RenderingJob renderingJob) {
        this.scene = scene;
        this.renderingJob = renderingJob;
    }

    public SceneInfo() {

    }

    private String scene;
    private State state;
    private RenderingJob renderingJob;
    private long totalBuckets;
}
