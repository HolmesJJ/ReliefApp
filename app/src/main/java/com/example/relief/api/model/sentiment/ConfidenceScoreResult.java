package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

public class ConfidenceScoreResult {

    private double positive;
    private double neutral;
    private double negative;

    public ConfidenceScoreResult() {
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public double getNeutral() {
        return neutral;
    }

    public void setNeutral(double neutral) {
        this.neutral = neutral;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }

    @NonNull
    @Override
    public String toString() {
        return "ConfidenceScoreResult{" + "positive=" + positive + ", neutral=" + neutral
                + ", negative=" + negative + '}';
    }
}
