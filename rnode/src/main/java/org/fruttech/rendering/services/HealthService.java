package org.fruttech.rendering.services;

import org.fruttech.rendering.common.RunnableService;
import org.fruttech.rendering.data.RNode;

/**
 * Cluster health service.
 * Performs over-cluster monitoring
 */
public interface HealthService extends RunnableService {
    void heartbeat(RNode node);
    void putState(String name, Object value);

    String getStateJson();
}
