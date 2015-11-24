package org.fruttech.rendering.data.jobs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.fruttech.rendering.serialization.SerializationUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.UUID;

public class RenderingJobPart implements Externalizable {
    private String scene;
    private String bucketInfo;
    private long frame;
    private long totalBuckets;
    private String jobPartId;

    public RenderingJobPart() {}

    public RenderingJobPart(String scene, String bucketInfo, long frame, long totalBuckets) {
        this.scene = scene;
        this.bucketInfo = bucketInfo;
        this.frame = frame;
        this.totalBuckets = totalBuckets;

        final String jobPartId = UUID.randomUUID().toString();
        setJobPartId(jobPartId);
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public void setTotalBuckets(long totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    public long getTotalBuckets() {
        return totalBuckets;
    }

    public void setTotalBuckets(int totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    public String getBucketInfo() {
        return bucketInfo;
    }

    public void setBucketInfo(String bucketInfo) {
        this.bucketInfo = bucketInfo;
    }

    public long getFrame() {
        return frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public String getJobPartId() {
        return jobPartId;
    }

    public void setJobPartId(String jobPartId) {
        this.jobPartId = jobPartId;
    }


    @Override public void writeExternal(ObjectOutput out) throws IOException {
        SerializationUtils.writeNullUTF(out, jobPartId);
        SerializationUtils.writeNullUTF(out, scene);
        SerializationUtils.writeNullUTF(out, bucketInfo);
        out.writeLong(frame);
        out.writeLong(totalBuckets);
    }

    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        jobPartId = SerializationUtils.readNullUTF(in);
        scene = SerializationUtils.readNullUTF(in);
        bucketInfo = SerializationUtils.readNullUTF(in);
        frame = in.readLong();
        totalBuckets = in.readLong();
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
                .append("jobPartId", jobPartId)
                .append("scene", scene)
                .append("frame", frame)
                .append("bucketInfo", bucketInfo)
                .append("totalBuckets", totalBuckets)
                .toString();
    }
}
