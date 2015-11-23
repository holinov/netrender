package org.fruttech.rendering.data;

import org.fruttech.rendering.data.jobs.RenderingJob;

public class SceneInfo {
    private String scene;
    private SceneState sceneState;
    private RenderingJob renderingJob;
    private long totalBuckets;
    public SceneInfo(String scene, RenderingJob renderingJob) {
        this.scene = scene;
        this.renderingJob = renderingJob;
    }
    public SceneInfo() {

    }
}
