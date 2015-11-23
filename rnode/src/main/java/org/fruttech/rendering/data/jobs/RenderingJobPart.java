package org.fruttech.rendering.data.jobs;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class RenderingJobPart extends RenderingJob {
    private String bucketInfo;
    private long frame;
    private int totalBuckets;
    private String jobPartId;

    public RenderingJobPart() {}

    public RenderingJobPart(String scene, long fromFrame, long toFrame, String bucketInfo, long frame, int totalBuckets) {
        super(scene, fromFrame, toFrame);
        this.bucketInfo = bucketInfo;
        this.frame = frame;
        this.totalBuckets = totalBuckets;
    }

    public static String toJson(RenderingJob job) {
        return gson.toJson(job);
    }

    public static RenderingJobPart fromJson(String json) {
        return gson.fromJson(json, RenderingJobPart.class);
    }

    /**
     * Getter for property 'totalBuckets'.
     *
     * @return Value for property 'totalBuckets'.
     */
    public int getTotalBuckets() {
        return totalBuckets;
    }

    /**
     * Setter for property 'totalBuckets'.
     *
     * @param totalBuckets Value to set for property 'totalBuckets'.
     */
    public void setTotalBuckets(int totalBuckets) {
        this.totalBuckets = totalBuckets;
    }

    /**
     * Getter for property 'bucketInfo'.
     *
     * @return Value for property 'bucketInfo'.
     */
    public String getBucketInfo() {
        return bucketInfo;
    }

    /**
     * Setter for property 'bucketInfo'.
     *
     * @param bucketInfo Value to set for property 'bucketInfo'.
     */
    public void setBucketInfo(String bucketInfo) {
        this.bucketInfo = bucketInfo;
    }

    /**
     * Getter for property 'frame'.
     *
     * @return Value for property 'frame'.
     */
    public long getFrame() {
        return frame;
    }

    /**
     * Setter for property 'frame'.
     *
     * @param frame Value to set for property 'frame'.
     */
    public void setFrame(long frame) {
        this.frame = frame;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("RenderingJobPart{");
        sb.append("bucketInfo='").append(bucketInfo).append('\'');
        sb.append(", frame=").append(frame);
        sb.append(", scene=").append(getScene());
        sb.append(", totalBuckets=").append(totalBuckets);
        sb.append('}');
        return sb.toString();
    }

    @Override public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        out.writeUTF(bucketInfo);
        out.writeLong(frame);
        out.writeInt(totalBuckets);
        out.writeUTF(jobPartId);
    }

    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        bucketInfo = in.readUTF();
        frame = in.readLong();
        totalBuckets = in.readInt();
        jobPartId = in.readUTF();
    }

    public String getJobPartId() {
        return jobPartId;
    }

    public void setJobPartId(String jobPartId) {
        this.jobPartId = jobPartId;
    }
}
