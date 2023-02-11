package com.example.relief.api.model.chatbot;

import androidx.annotation.NonNull;

import java.util.List;

public class DialogResult {

    private boolean isContextOnly;
    private List<PromptResult> prompts;

    public DialogResult() {
    }

    public boolean isContextOnly() {
        return isContextOnly;
    }

    public void setContextOnly(boolean contextOnly) {
        isContextOnly = contextOnly;
    }

    public List<PromptResult> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<PromptResult> prompts) {
        this.prompts = prompts;
    }

    @NonNull
    @Override
    public String toString() {
        return "ContextResult{" + "isContextOnly=" + isContextOnly + ", prompts=" + prompts + '}';
    }
}
