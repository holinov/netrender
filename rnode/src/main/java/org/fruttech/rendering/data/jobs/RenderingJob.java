package org.fruttech.rendering.data.jobs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class RenderingJob implements Externalizable {
    final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String scene;
    private long fromFrame;
    private long toFrame;
    private String id;

    public RenderingJob() {
    }

    public RenderingJob(String scene, long fromFrame, long toFrame) {
        this.scene = scene;
        this.fromFrame = fromFrame;
        this.toFrame = toFrame;
    }

    public static String toJson(RenderingJob job) {
        return gson.toJson(job);
    }

    public static RenderingJob fromJson(String json) {
        return gson.fromJson(json, RenderingJob.class);
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
     * Getter for property 'fromFrame'.
     *
     * @return Value for property 'fromFrame'.
     */
    public long getFromFrame() {
        return fromFrame;
    }

    /**
     * Setter for property 'fromFrame'.
     *
     * @param fromFrame Value to set for property 'fromFrame'.
     */
    public void setFromFrame(long fromFrame) {
        this.fromFrame = fromFrame;
    }

    /**
     * Getter for property 'toFrame'.
     *
     * @return Value for property 'toFrame'.
     */
    public long getToFrame() {
        return toFrame;
    }

    /**
     * Setter for property 'toFrame'.
     *
     * @param toFrame Value to set for property 'toFrame'.
     */
    public void setToFrame(long toFrame) {
        this.toFrame = toFrame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(scene);
        out.writeLong(fromFrame);
        out.writeLong(toFrame);
        out.writeUTF(id);
    }

    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        scene = in.readUTF();
        fromFrame = in.readLong();
        toFrame = in.readLong();
        id = in.readUTF();
    }
}
