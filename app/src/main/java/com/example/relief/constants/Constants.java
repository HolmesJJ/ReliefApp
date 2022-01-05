package com.example.relief.constants;

public final class Constants {

    public static final int PERMISSION_REQUEST_CODE = 101;

    // OCR
    public static final String ACCESS_TOKE_URL = "https://aip.baidubce.com/oauth/2.0/token?";
    public static final String OCR_API_KEY = "ftmmSbsxIH1rwM63imqMerPG";
    public static final String OCR_SECRET_KEY = "R6E1lzQ9Aevw8vdvCWETMRFwXEntvzlL";
    public static final String OCR_STANDARD_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?";
    public static final String OCR_WEB_IMAGE_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage?";

    // Sentiment Analytics
    public static final String SENTIMENT_URL = "https://relief-sentiment.cognitiveservices.azure.com/text/analytics/v3.2-preview.1/sentiment?";
    public static final String SENTIMENT_KEY_CONTENT_TYPE = "Ocp-Apim-Subscription-Key";
    public static final String SENTIMENT_KEY_1 = "6a9308cd1d574ed591851a3fda4188e9";
    public static final String SENTIMENT_KEY_2 = "f6798df2f25e438689ac0d239c6a1c61";

    private Constants() {
    }
}
