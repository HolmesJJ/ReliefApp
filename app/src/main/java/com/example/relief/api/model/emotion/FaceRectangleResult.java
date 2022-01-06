package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FaceRectangleResult {

    private int top;
    private int left;
    private int width;
    private int height;

    public FaceRectangleResult() {
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceRectangleResult{" + "top=" + top + ", left=" + left
                + ", width=" + width + ", height=" + height + '}';
    }
}
