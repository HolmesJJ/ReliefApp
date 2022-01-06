package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

import java.util.List;

public class HairResult {

    private double bald;
    private boolean invisible;
    private List<HairColorResult> hairColor;

    public double getBald() {
        return bald;
    }

    public void setBald(double bald) {
        this.bald = bald;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public List<HairColorResult> getHairColor() {
        return hairColor;
    }

    public void setHairColor(List<HairColorResult> hairColor) {
        this.hairColor = hairColor;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hair{" + "bald=" + bald + ", invisible=" + invisible + ", hairColor=" + hairColor + '}';
    }
}
