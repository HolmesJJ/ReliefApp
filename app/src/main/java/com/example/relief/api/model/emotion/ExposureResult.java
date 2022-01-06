package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class ExposureResult {

    private String exposureLevel;
    private double value;

    public ExposureResult() {
    }

    public String getExposureLevel() {
        return exposureLevel;
    }

    public void setExposureLevel(String exposureLevel) {
        this.exposureLevel = exposureLevel;
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
        return "ExposureResult{" + "exposureLevel='" + exposureLevel + '\'' + ", value=" + value + '}';
    }
}
