package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class OcclusionResult {

    private boolean foreheadOccluded;
    private boolean eyeOccluded;
    private boolean mouthOccluded;

    public OcclusionResult() {
    }

    public boolean isForeheadOccluded() {
        return foreheadOccluded;
    }

    public void setForeheadOccluded(boolean foreheadOccluded) {
        this.foreheadOccluded = foreheadOccluded;
    }

    public boolean isEyeOccluded() {
        return eyeOccluded;
    }

    public void setEyeOccluded(boolean eyeOccluded) {
        this.eyeOccluded = eyeOccluded;
    }

    public boolean isMouthOccluded() {
        return mouthOccluded;
    }

    public void setMouthOccluded(boolean mouthOccluded) {
        this.mouthOccluded = mouthOccluded;
    }

    @NonNull
    @Override
    public String toString() {
        return "OcclusionResult{" + "foreheadOccluded=" + foreheadOccluded
                + ", eyeOccluded=" + eyeOccluded + ", mouthOccluded=" + mouthOccluded + '}';
    }
}
