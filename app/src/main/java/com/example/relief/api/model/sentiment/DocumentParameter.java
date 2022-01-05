package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

public class DocumentParameter {

    private int id;
    private String text;

    public DocumentParameter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return "DocumentParameter{" + "id=" + id + ", text='" + text + '\'' + '}';
    }
}
