package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FaceBase64Parameter {

    private int id;
    private String base64;

    public FaceBase64Parameter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceBase64Parameter{" + "id=" + id + ", base64='" + base64 + '\'' + '}';
    }
}
