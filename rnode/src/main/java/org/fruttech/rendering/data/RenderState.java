package org.fruttech.rendering.data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * RNode state
 */
public class RenderState implements Externalizable {
    private boolean isBusy;
    private String currentScene;
    private String currentFrame;
    private String currentBucket;

    public String getCurrentBucket() {
        return currentBucket;
    }

    public void setCurrentBucket(String currentBucket) {
        this.currentBucket = currentBucket;
    }

    public String getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(String currentFrame) {
        this.currentFrame = currentFrame;
    }

    public String getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(String currentScene) {
        this.currentScene = currentScene;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setIsBuisy(boolean isBuisy) {
        this.isBusy = isBuisy;
    }


    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(isBusy);
        out.writeUTF(currentScene);
        out.writeUTF(currentFrame);
        out.writeUTF(currentBucket);
    }

    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        isBusy = in.readBoolean();
        currentScene = in.readUTF();
        currentFrame = in.readUTF();
        currentBucket = in.readUTF();
    }
}

