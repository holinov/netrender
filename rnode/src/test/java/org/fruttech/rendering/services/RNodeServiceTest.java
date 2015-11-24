package org.fruttech.rendering.services;

import junit.framework.TestCase;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RNodeType;
import org.junit.Assert;

public class RNodeServiceTest extends TestCase {

    public void testGetCurrentRNode() throws Exception {
        final RNodeService nodeService = new RNodeService();
        final RNode currentRNode = nodeService.getCurrentRNode();

        Assert.assertNotEquals(RNodeService.UNRESOLVED_HOST, currentRNode.nodeId());
        Assert.assertEquals(RNodeType.RenderNode, currentRNode.nodeType());
    }
}