package org.fruttech.rendering.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.fruttech.rendering.data.jobs.RenderingJobPart;

import java.io.*;
import java.util.Map;

public class RenderingJobPartSerializer
        implements Serializer<RenderingJobPart>, Deserializer<RenderingJobPart> {
    @Override public void configure(Map<String, ?> configs, boolean isKey) { }

    @Override public RenderingJobPart deserialize(String topic, byte[] data) {
        try (final ByteArrayInputStream bais = new ByteArrayInputStream(data);
             final ObjectInputStream ois = new ObjectInputStream(bais)) {
            final RenderingJobPart renderingJob = new RenderingJobPart();
            renderingJob.readExternal(ois);
            return renderingJob;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override public byte[] serialize(String topic, RenderingJobPart data) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
             final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            data.writeExternal(oos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    @Override public void close() { }
}
