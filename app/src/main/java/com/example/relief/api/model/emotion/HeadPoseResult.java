package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class HeadPoseResult {

    private double pitch;
    private double roll;
    private double yaw;

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    @NonNull
    @Override
    public String toString() {
        return "HeadPoseResult{" + "pitch=" + pitch + ", roll=" + roll + ", yaw=" + yaw + '}';
    }
}
