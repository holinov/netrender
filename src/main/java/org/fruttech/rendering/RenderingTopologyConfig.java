package org.fruttech.rendering;

import com.google.inject.Inject;

import javax.inject.Named;

public class RenderingTopologyConfig{
    @Inject @Named("rendering.topology.spouts")
    public int spouts;

    @Inject @Named("rendering.topology.preprocessors")
    public int preprocessors;

    @Inject @Named("rendering.topology.renderers")
    public int renderers;

    @Inject @Named("rendering.topology.combiners")
    public int combiners;
}
