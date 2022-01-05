package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;

public class WordResult {

    private String word;

    public WordResult() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @NonNull

    @Override
    public String toString() {
        return "WordResult{" + "word='" + word + '\'' + '}';
    }
}
