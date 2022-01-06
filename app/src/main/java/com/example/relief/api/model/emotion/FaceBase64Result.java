package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FaceBase64Result {

    private int code;

    public FaceBase64Result() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceBase64Result{" + "code=" + code + '}';
    }
}
