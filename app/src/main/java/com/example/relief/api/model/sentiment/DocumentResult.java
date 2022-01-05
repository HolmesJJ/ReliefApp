package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

import java.util.List;

public class DocumentResult {

    private int id;
    private String sentiment;
    private List<ConfidenceScoreResult> confidenceScores;
    private List<SentenceResult> sentences;
    private List<WarningResult> warnings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public List<ConfidenceScoreResult> getConfidenceScores() {
        return confidenceScores;
    }

    public void setConfidenceScores(List<ConfidenceScoreResult> confidenceScores) {
        this.confidenceScores = confidenceScores;
    }

    public List<SentenceResult> getSentences() {
        return sentences;
    }

    public void setSentences(List<SentenceResult> sentences) {
        this.sentences = sentences;
    }

    public List<WarningResult> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<WarningResult> warnings) {
        this.warnings = warnings;
    }

    @NonNull
    @Override
    public String toString() {
        return "DocumentResult{"
                + "id=" + id + ", sentiment='" + sentiment + '\'' + ", confidenceScores=" + confidenceScores
                + ", sentences=" + sentences + ", warnings=" + warnings + '}';
    }
}
