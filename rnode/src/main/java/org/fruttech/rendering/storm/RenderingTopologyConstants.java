package org.fruttech.rendering.storm;

public class RenderingTopologyConstants {
    public static final String JOB_SPOUT_NAME = "job_spout";
    public static final String PREPROCESSOR_NAME = "preprocessor";
    public static final String RENDERER_NAME = "renderer";
    public static final String COMBINER_NAME = "combiner";

    public static final String TOPOLOGY_NAME = "RenderingTopology";

    public static final String KEY_FIELD_NAME = "key";
    public static final String DATA_FIELD_NAME = "data";

    public static final String RENDER_JOBS_QUEUE_NAME = "RenderJobs_TT";
    public static final String RENDER_JOB_PARTS_QUEUE_NAME = "RenderJobParts";
}
