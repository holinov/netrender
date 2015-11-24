package org.fruttech.rendering.data.jobs;

public class RenderingJobPartResult extends RenderingJobPart {
    private byte[] result;

    public RenderingJobPartResult(String scene, String bucketInfo, long frame, long totalBuckets, byte[] result) {
        super(scene, bucketInfo, frame, totalBuckets);
        this.result = result;
    }

    public RenderingJobPartResult() {}

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    @Override public String toString() {
        return super.toString();
    }
}
