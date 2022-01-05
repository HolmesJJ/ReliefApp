package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

import java.util.List;

public class SentimentResult {

    private List<DocumentResult> documents;
    private List<ErrorResult> errors;
    private String modelVersion;

    public SentimentResult() {
    }

    public List<DocumentResult> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentResult> documents) {
        this.documents = documents;
    }

    public List<ErrorResult> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorResult> errors) {
        this.errors = errors;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    @NonNull
    @Override
    public String toString() {
        return "SentimentResult{" + "documents=" + documents + ", errors=" + errors
                + ", modelVersion=" + modelVersion + '}';
    }
}
