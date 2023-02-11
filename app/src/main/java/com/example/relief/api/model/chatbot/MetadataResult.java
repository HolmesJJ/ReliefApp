package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

public class MetadataResult {

    private String editorial;

    public MetadataResult() {
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @NonNull
    @Override
    public String toString() {
        return "MetadataResult{" + "editorial='" + editorial + '\'' + '}';
    }
}
