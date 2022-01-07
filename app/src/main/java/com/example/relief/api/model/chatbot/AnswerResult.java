package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

import java.util.List;

public class AnswerResult {

    private int id;
    private List<String> questions;
    private String answer;
    private double score;
    private String source;
    private boolean isDocumentText;
    private List<MetadataResult> metadata;
    private ContextResult context;

    public AnswerResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isDocumentText() {
        return isDocumentText;
    }

    public void setDocumentText(boolean documentText) {
        isDocumentText = documentText;
    }

    public List<MetadataResult> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResult> metadata) {
        this.metadata = metadata;
    }

    public ContextResult getContext() {
        return context;
    }

    public void setContext(ContextResult context) {
        this.context = context;
    }

    @NonNull

    @Override
    public String toString() {
        return "AnswerResult{" + "id=" + id + ", questions=" + questions + ", answer='" + answer + '\''
                + ", score=" + score + ", source='" + source + '\'' + ", isDocumentText=" + isDocumentText
                + ", metadata=" + metadata + ", context=" + context + '}';
    }
}
