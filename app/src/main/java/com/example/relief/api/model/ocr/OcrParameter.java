package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;

public class OcrParameter {

    private String image;

    public OcrParameter() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull

    @Override
    public String toString() {
        return "OcrParameter{" + "image='" + image + '\'' + '}';
    }
}
