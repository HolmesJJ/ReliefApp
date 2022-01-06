package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class HairColorResult {

    private String color;
    private double confidence;

    public HairColorResult() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @NonNull
    @Override
    public String toString() {
        return "HairColorResult{" + "color='" + color + '\'' + ", confidence=" + confidence + '}';
    }
}
