package com.example.relief.api.model.emotion;

public enum EmotionEnum {

    ANGER("anger"),
    CONTEMPT("contempt"),
    DISGUST("disgust"),
    FEAR("fear"),
    HAPPINESS("happiness"),
    NEUTRAL("neutral"),
    SADNESS("sadness"),
    SURPRISE("surprise");

    private final String value;

    EmotionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "EmotionEnum{" + "value='" + value + '\'' + '}';
    }
}
