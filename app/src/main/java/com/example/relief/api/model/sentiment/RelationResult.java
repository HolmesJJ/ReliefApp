package com.example.relief.api.model.sentiment;

import androidx.annotation.NonNull;

public class RelationResult {

    private String relationType;
    private String ref;

    public RelationResult() {
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @NonNull
    @Override
    public String toString() {
        return "RelationResult{" + "relationType='" + relationType + '\'' + ", ref='" + ref + '\'' + '}';
    }
}
