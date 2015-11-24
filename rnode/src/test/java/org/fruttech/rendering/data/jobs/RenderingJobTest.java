package org.fruttech.rendering.data.jobs;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RenderingJobTest extends TestCase {

    public void testWriteReadExternal() throws Exception {
        final RenderingJob job1 = new RenderingJob("scene-test", 0, 10);
        byte[] buff;

        try (
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os)
        ) {
            job1.writeExternal(oos);
            os.flush();
            buff = os.toByteArray();

        }

        try (
                ByteArrayInputStream is = new ByteArrayInputStream(buff);
                ObjectInputStream ois = new ObjectInputStream(is)
        ) {
            final RenderingJob job2 = new RenderingJob();
            job2.readExternal(ois);

            Assert.assertEquals(job1.getScene(), job2.getScene());
            Assert.assertEquals(job1.getFromFrame(), job2.getFromFrame());
            Assert.assertEquals(job1.getToFrame(), job2.getToFrame());
        }
    }

}