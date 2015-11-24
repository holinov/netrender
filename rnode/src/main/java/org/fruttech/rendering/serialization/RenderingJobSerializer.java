package org.fruttech.rendering.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.fruttech.rendering.data.jobs.RenderingJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class RenderingJobSerializer
        implements Serializer<RenderingJob>, Deserializer<RenderingJob> {
    private static final Logger logger = LoggerFactory.getLogger(RenderingJobSerializer.class);

    @Override public void configure(Map<String, ?> configs, boolean isKey) { }

    @Override public RenderingJob deserialize(String topic, byte[] data) {
        try (final ByteArrayInputStream bais = new ByteArrayInputStream(data);
             final ObjectInputStream ois = new ObjectInputStream(bais)) {
            final RenderingJob renderingJob = new RenderingJob();
            renderingJob.readExternal(ois);
            return renderingJob;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Can't deserialize RenderingJobSerializer", e);
            return null;
        }
    }

    @Override public byte[] serialize(String topic, RenderingJob data) {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            data.writeExternal(oos);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("Can't serialize RenderingJobSerializer", e);
            return null;
        }
    }

    @Override public void close() { }
}


