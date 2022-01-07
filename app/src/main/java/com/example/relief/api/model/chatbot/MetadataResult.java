package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

public class MetadataResult {

    private String name;
    private String value;

    public MetadataResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "MetadataResult{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
    }
}
