package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class NoiseResult {

    private String noiseLevel;
    private double value;

    public NoiseResult() {
    }

    public String isNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(String noiseLevel) {
        this.noiseLevel = noiseLevel;
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
        return "NoiseResult{" + "noiseLevel=" + noiseLevel + ", value=" + value + '}';
    }
}
