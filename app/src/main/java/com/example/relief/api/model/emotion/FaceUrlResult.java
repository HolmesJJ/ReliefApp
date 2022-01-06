package com.example.relief.api.model.emotion;

import androidx.annotation.NonNull;

public class FaceUrlResult {

    private String faceId;
    private FaceRectangleResult faceRectangle;
    private FaceAttributesResult faceAttributes;

    public FaceUrlResult() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FaceRectangleResult getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangleResult faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public FaceAttributesResult getFaceAttributes() {
        return faceAttributes;
    }

    public void setFaceAttributes(FaceAttributesResult faceAttributes) {
        this.faceAttributes = faceAttributes;
    }

    @NonNull
    @Override
    public String toString() {
        return "FaceResult{" + "faceId='" + faceId + '\'' + ", faceRectangle=" + faceRectangle
                + ", faceAttributes=" + faceAttributes + '}';
    }
}
