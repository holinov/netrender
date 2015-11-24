package org.fruttech.rendering.data;

import org.fruttech.rendering.data.jobs.RenderingJob;

import java.io.Serializable;

public class SceneInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String scene;
    private SceneState sceneState = SceneState.Queued;
    private RenderingJob renderingJob;
    private long totalBuckets;

    public SceneInfo(String scene, RenderingJob renderingJob, long totalBuckets) {
        this.scene = scene;
        this.renderingJob = renderingJob;
        this.totalBuckets = totalBuckets;
    }

    public SceneInfo() {

    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("SceneInfo{");
        sb.append("scene='").append(scene).append('\'');
        sb.append(", sceneState=").append(sceneState);
        sb.append(", totalBuckets=").append(totalBuckets);
        sb.append(", renderingJob=").append(renderingJob);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Getter for property 'scene'.
     *
     * @return Value for property 'scene'.
     */
    public String getScene() {
        return scene;
    }

    /**
     * Setter for property 'scene'.
     *
     * @param scene Value to set for property 'scene'.
     */
    public void setScene(String scene) {
        this.scene = scene;
    }

    /**
     * Getter for property 'sceneState'.
     *
     * @return Value for property 'sceneState'.
     */
    public SceneState getSceneState() {
        return sceneState;
    }

    /**
     * Setter for property 'sceneState'.
     *
     * @param sceneState Value to set for property 'sceneState'.
     */
    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }

    /**
     * Getter for property 'renderingJob'.
     *
     * @return Value for property 'renderingJob'.
     */
    public RenderingJob getRenderingJob() {
        return renderingJob;
    }

    /**
     * Setter for property 'renderingJob'.
     *
     * @param renderingJob Value to set for property 'renderingJob'.
     */
    public void setRenderingJob(RenderingJob renderingJob) {
        this.renderingJob = renderingJob;
    }

    /**
     * Getter for property 'totalBuckets'.
     *
     * @return Value for property 'totalBuckets'.
     */
    public long getTotalBuckets() {
        return totalBuckets;
    }

    /**
     * Setter for property 'totalBuckets'.
     *
     * @param totalBuckets Value to set for property 'totalBuckets'.
     */
    public void setTotalBuckets(long totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SceneInfo sceneInfo = (SceneInfo) o;

        return getScene().equals(sceneInfo.getScene());

    }

    @Override public int hashCode() {
        return getScene().hashCode();
    }
}
