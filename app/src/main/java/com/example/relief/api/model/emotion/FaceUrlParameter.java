package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FaceUrlParameter {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceParameter{" + "url='" + url + '\'' + '}';
    }
}
