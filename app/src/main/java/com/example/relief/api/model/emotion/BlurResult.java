package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class BlurResult {

    private String blurLevel;
    private double value;

    public BlurResult() {
    }

    public String getBlurLevel() {
        return blurLevel;
    }

    public void setBlurLevel(String blurLevel) {
        this.blurLevel = blurLevel;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @NonNull

    @Override
    public String toString() {
        return "BlurResult{" + "blurLevel='" + blurLevel + '\'' + ", value=" + value + '}';
    }
}
