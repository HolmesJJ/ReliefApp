package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

import java.util.List;

public class AnswersResult {

    private List<AnswerResult> answers;

    public AnswersResult() {
    }

    public List<AnswerResult> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResult> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnswersResult{" + "answers=" + answers + '}';
    }
}
