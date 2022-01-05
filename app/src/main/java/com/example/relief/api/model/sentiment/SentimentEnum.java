package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

public enum SentimentEnum {

    POSITIVE("positive"),
    NEUTRAL("neutral"),
    NEGATIVE("negative");

    private final String value;

    SentimentEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return "SentimentEnum{" + "value='" + value + '\'' + '}';
    }
}
