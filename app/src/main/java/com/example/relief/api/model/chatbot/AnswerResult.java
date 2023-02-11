package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

import java.util.List;

public class AnswerResult {

    private int id;
    private List<String> questions;
    private String answer;
    private double confidenceScore;
    private String source;
    private List<MetadataResult> metadata;
    private DialogResult dialog;

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

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<MetadataResult> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResult> metadata) {
        this.metadata = metadata;
    }

    public DialogResult getDialog() {
        return dialog;
    }

    public void setDialog(DialogResult dialog) {
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnswerResult{" + "id=" + id + ", questions=" + questions + ", answer='" + answer + '\''
                + ", confidenceScore=" + confidenceScore + ", source='" + source + '\''
                + ", metadata=" + metadata + ", dialog=" + dialog + '}';
    }
}
