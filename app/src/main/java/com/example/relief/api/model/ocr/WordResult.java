package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;

public class WordResult {

    private String words;

    public WordResult() {
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @NonNull

    @Override
    public String toString() {
        return "WordResult{" + "words='" + words + '\'' + '}';
    }
}
