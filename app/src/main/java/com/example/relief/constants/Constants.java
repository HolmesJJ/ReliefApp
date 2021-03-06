package com.example.relief.constants;

public final class Constants {

    public static final int PERMISSION_REQUEST_CODE = 101;

    // Server
    public static final String HTTP_SERVER_URL = "http://47.241.162.204/api/";
    public static final String HTTPS_SERVER_URL = "https://9c62-47-241-162-204.ngrok.io/api/";
    public static final String UPLOAD_FACE_URL = HTTP_SERVER_URL + "upload_face.php";
    public static final String FACE_DIRECTORY_URL = HTTPS_SERVER_URL + "face/";

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

    // Emotion
    public static final String EMOTION_URL = "https://relief-emotion.cognitiveservices.azure.com/face/v1.0/detect?";
    public static final String EMOTION_KEY_CONTENT_TYPE = "Ocp-Apim-Subscription-Key";
    public static final String EMOTION_KEY_1 = "b2447abafde745cba43a586888f4504b";
    public static final String EMOTION_KEY_2 = "3d0a62bf07934d3cab7b69a7e6ffeca7";

    // Chatbot
    public static final String CHATBOT_URL
            = "https://silverplus-chatbot.azurewebsites.net/qnamaker/knowledgebases/65148328-b5b2-4ce6-831c-50499e296d93/generateAnswer";
    public static final String CHATBOT_CONTENT_TYPE = "Authorization";
    public static final String CHATBOT_KEY_1 = "EndpointKey 805d2b34-f07e-4148-93c2-bbd0e7317f72";

    private Constants() {
    }
}
