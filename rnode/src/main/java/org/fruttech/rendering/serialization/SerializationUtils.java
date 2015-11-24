package org.fruttech.rendering.serialization;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SerializationUtils {
    public static void writeNullUTF(ObjectOutput out, String string) throws IOException {
        if (string != null) {
            out.writeBoolean(true);
            out.writeUTF(string);
        } else {
            out.writeBoolean(false);
        }
    }

    public static String readNullUTF(ObjectInput in) throws IOException {
        boolean hasScene = in.readBoolean();
        return hasScene ? in.readUTF() : null;
    }
}
