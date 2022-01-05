package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

import java.util.List;

public class SentimentParameter {

    private List<DocumentParameter> documents;

    public SentimentParameter() {
    }

    public List<DocumentParameter> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentParameter> documents) {
        this.documents = documents;
    }

    @NonNull
    @Override
    public String toString() {
        return "SentimentParameter{" + "documents=" + documents + '}';
    }
}
