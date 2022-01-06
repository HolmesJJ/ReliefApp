package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class MakeupResult {

    private boolean eyeMakeup;
    private boolean lipMakeup;

    public MakeupResult() {
    }

    public boolean isEyeMakeup() {
        return eyeMakeup;
    }

    public void setEyeMakeup(boolean eyeMakeup) {
        this.eyeMakeup = eyeMakeup;
    }

    public boolean isLipMakeup() {
        return lipMakeup;
    }

    public void setLipMakeup(boolean lipMakeup) {
        this.lipMakeup = lipMakeup;
    }

    @NonNull
    @Override
    public String toString() {
        return "MakeupResult{" + "eyeMakeup=" + eyeMakeup + ", lipMakeup=" + lipMakeup + '}';
    }
}
