package org.fruttech.rendering.data.jobs;

public class RenderingJobPartResult extends RenderingJobPart{
    private byte[] result;

    public RenderingJobPartResult(String scene, long fromFrame, long toFrame, String bucketInfo, long frame, int totalBuckets, byte[] result) {
        super(scene, fromFrame, toFrame, bucketInfo, frame,totalBuckets);
        this.result = result;
    }

    public RenderingJobPartResult() {}

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }
    public static String toJson(RenderingJob job){
        return gson.toJson(job);
    }

    public static RenderingJobPartResult fromJson(String json) {
        return gson.fromJson(json, RenderingJobPartResult.class);
    }
}
