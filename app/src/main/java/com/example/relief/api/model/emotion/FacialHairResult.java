package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FacialHairResult {

    private double moustache;
    private double beard;
    private double sideburns;

    public FacialHairResult() {
    }

    public double getMoustache() {
        return moustache;
    }

    public void setMoustache(double moustache) {
        this.moustache = moustache;
    }

    public double getBeard() {
        return beard;
    }

    public void setBeard(double beard) {
        this.beard = beard;
    }

    public double getSideburns() {
        return sideburns;
    }

    public void setSideburns(double sideburns) {
        this.sideburns = sideburns;
    }

    @NonNull
    @Override
    public String toString() {
        return "FacialHairResult{" + "moustache=" + moustache + ", beard=" + beard
                + ", sideburns=" + sideburns + '}';
    }
}
