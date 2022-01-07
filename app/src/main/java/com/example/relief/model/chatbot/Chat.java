package com.example.relief.model.chatbot;

public class Chat {

    private String content;
    // 0是User，1是对方
    private int type;

    public Chat(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
