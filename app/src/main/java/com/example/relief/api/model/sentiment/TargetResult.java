package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

import java.util.List;

public class TargetResult {

    private String sentiment;
    private List<ConfidenceScoreResult> confidenceScores;
    private int offset;
    private int length;
    private String text;
    private List<RelationResult> relations;

    public TargetResult() {
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RelationResult> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationResult> relations) {
        this.relations = relations;
    }

    @NonNull
    @Override
    public String toString() {
        return "TargetResult{" + "sentiment='" + sentiment + '\'' + ", confidenceScores=" + confidenceScores
                + ", offset=" + offset + ", length=" + length + ", text=" + text + ", relations=" + relations
                + '}';
    }
}
