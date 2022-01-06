package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

import java.util.List;

public class FacesUrlResult {

    private List<FaceUrlResult> list;

    public FacesUrlResult() {
    }

    public List<FaceUrlResult> getList() {
        return list;
    }

    public void setList(List<FaceUrlResult> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public String toString() {
        return "FacesUrlResult{" + "list=" + list + '}';
    }
}
