package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

public class QuestionParameter {

    private String question;

    public QuestionParameter() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionParameter{" + "question='" + question + '\'' + '}';
    }
}
