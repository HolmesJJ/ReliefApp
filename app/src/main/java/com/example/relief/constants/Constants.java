package com.example.relief.constants;

public final class Constants {

    public static final int PERMISSION_REQUEST_CODE = 101;

    // Server
    public static final String HTTP_SERVER_URL = "http://47.241.162.204/api/";
    public static final String HTTPS_SERVER_URL = "https://ac1b-8-219-72-44.ngrok.io/api/";
    public static final String UPLOAD_FACE_URL = HTTP_SERVER_URL + "upload_face.php";
    public static final String FACE_DIRECTORY_URL = HTTPS_SERVER_URL + "face/";
    public static final String ACCESS_TOKE_URL = "https://aip.baidubce.com/oauth/2.0/token?";

    // OCR
    public static final String OCR_API_KEY = "ftmmSbsxIH1rwM63imqMerPG";
    public static final String OCR_SECRET_KEY = "R6E1lzQ9Aevw8vdvCWETMRFwXEntvzlL";
    public static final String OCR_STANDARD_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?";
    public static final String OCR_WEB_IMAGE_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage?";

    // Baidu Emotion
    public static final String EMOTION_API_KEY = "QqSZSHOr43W7xSi69TEVRp7n";
    public static final String EMOTION_SECRET_KEY = "tuLDl0SKdzFKsP8SHEWm9p0GkXCDcObA";
    public static final String EMOTION_STANDARD_URL = "https://aip.baidubce.com/rest/2.0/face/v3/detect";

    // Sentiment Analytics
    public static final String SENTIMENT_URL = "https://reliefsg-language.cognitiveservices.azure.com/text/analytics/v3.2-preview.1/sentiment?";
    public static final String SENTIMENT_KEY_CONTENT_TYPE = "Ocp-Apim-Subscription-Key";
    public static final String SENTIMENT_KEY_1 = "d96e3b03f7e0477196864fb7f6edf711";
    public static final String SENTIMENT_KEY_2 = "eee0f00dcc674a6193173d1b0d09237e";

    // Microsoft Emotion
    public static final String EMOTION_URL = "https://reliefsg-emotion.cognitiveservices.azure.com/face/v1.0/detect?";
    public static final String EMOTION_KEY_CONTENT_TYPE = "Ocp-Apim-Subscription-Key";
    public static final String EMOTION_KEY_1 = "820ddf68a2c74bc1ba541d71a31129aa";
    public static final String EMOTION_KEY_2 = "356828bd88084ad29f5b8bd98d7097da";

    // Chatbot
    public static final String CHATBOT_URL = "https://reliefsg-language.cognitiveservices.azure.com/language/:query-knowledgebases?";
    public static final String CHATBOT_CONTENT_TYPE = "Ocp-Apim-Subscription-Key";
    public static final String CHATBOT_KEY_1 = "d96e3b03f7e0477196864fb7f6edf711";
    public static final String CHATBOT_KEY_2 = "eee0f00dcc674a6193173d1b0d09237e";

    private Constants() {
    }
}
