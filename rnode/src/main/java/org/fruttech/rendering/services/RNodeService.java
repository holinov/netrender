package org.fruttech.rendering.services;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.fruttech.rendering.data.RNode;
import org.fruttech.rendering.data.RNodeType;
import org.fruttech.rendering.serialization.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RNodeService {
    public static final String UNRESOLVED_HOST = "UNRESOLVED HOST";
    private static final Logger logger = LoggerFactory.getLogger(RNodeService.class);

    public RNode getCurrentRNode() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            logger.warn("Unable to resolve hostname");
            try {
                hostName = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
                logger.error("Unable to resolve host address");
                hostName = UNRESOLVED_HOST;
            }
        }

        return new RNodeImpl(hostName, RNodeType.RenderNode);
    }

    private static class RNodeImpl implements RNode {
        private String nodeId;
        private RNodeType nodeType;

        public RNodeImpl() {
        }

        public RNodeImpl(String nodeId, RNodeType nodeType) {
            this.nodeId = nodeId;
            this.nodeType = nodeType;
        }

        @Override public String nodeId() {
            return nodeId;
        }

        @Override public RNodeType nodeType() {
            return nodeType;
        }

        @Override public void writeExternal(ObjectOutput out) throws IOException {
            SerializationUtils.writeNullUTF(out, nodeId);
            out.writeObject(nodeType);
        }

        @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            nodeId = SerializationUtils.readNullUTF(in);
            nodeType = (RNodeType) in.readObject();
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof RNodeImpl)) return false;

            RNodeImpl rNode = (RNodeImpl) o;

            return new EqualsBuilder()
                    .append(nodeId, rNode.nodeId)
                    .append(nodeType, rNode.nodeType)
                    .isEquals();
        }

        @Override public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(nodeId)
                    .append(nodeType)
                    .toHashCode();
        }

        @Override public String toString() {
            return new ToStringBuilder(this)
                    .append("nodeType", nodeType)
                    .append("nodeId", nodeId)
                    .toString();
        }
    }
}
