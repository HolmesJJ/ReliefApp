package com.example.relief.api;

import androidx.annotation.NonNull;
import com.example.relief.api.model.emotion.FaceBase64Parameter;
import com.example.relief.api.model.emotion.FaceBase64Result;
import com.example.relief.api.model.emotion.FaceUrlParameter;
import com.example.relief.api.model.emotion.FacesUrlResult;
import com.example.relief.api.model.ocr.AccessTokenResult;
import com.example.relief.api.model.ocr.OcrParameter;
import com.example.relief.api.model.ocr.OcrResult;
import com.example.relief.api.model.sentiment.DocumentParameter;
import com.example.relief.api.model.sentiment.SentimentParameter;
import com.example.relief.api.model.sentiment.SentimentResult;
import com.example.relief.config.Config;
import com.example.relief.constants.Constants;
import com.example.relief.network.http.Request;
import com.example.relief.network.http.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 标准http接口请求管理类
 */
public final class ApiClient {

    private ApiClient() {
    }

    @NonNull
    public static Result<AccessTokenResult> getOcrAccessToken() {
        Request request = new Request().setPath(Constants.ACCESS_TOKE_URL
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + Constants.OCR_API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + Constants.OCR_SECRET_KEY)
                .setMethod(Request.RequestMethod.POST.value());
        return ExecutorRequest.execute(request);
    }

    @NonNull
    public static Result<OcrResult> ocr(String image) {
        OcrParameter ocrParameter = new OcrParameter();
        ocrParameter.setImage(image);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Request request = new Request().setPath(Constants.OCR_STANDARD_URL
                + "access_token=" + Config.getOcrAccessToken())
                .setHeaderMap(headers)
                .setMethod(Request.RequestMethod.POST.value())
                .setBody(ocrParameter);
        return ExecutorRequest.execute(request);
    }

    @NonNull
    public static Result<SentimentResult> sentimentAnalysis(String content) {
        DocumentParameter documentParameter = new DocumentParameter();
        documentParameter.setId(1);
        documentParameter.setText(content);
        SentimentParameter sentimentParameter = new SentimentParameter();
        List<DocumentParameter> documentParameters = new ArrayList<>();
        documentParameters.add(documentParameter);
        sentimentParameter.setDocuments(documentParameters);
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Constants.SENTIMENT_KEY_CONTENT_TYPE, Constants.SENTIMENT_KEY_1);
        Request request = new Request().setPath(Constants.SENTIMENT_URL
                // Provides granular information about assessments (adjectives) related to targets (nouns) in the text
                + "opinionMining=" + "true")
                .setHeaderMap(headers)
                .setMethod(Request.RequestMethod.POST.value())
                .setBody(sentimentParameter);
        return ExecutorRequest.execute(request);
    }

    @NonNull
    public static Result<FaceBase64Result> uploadFace(String base64) {
        FaceBase64Parameter faceBase64Parameter = new FaceBase64Parameter();
        faceBase64Parameter.setId(Config.getUserId());
        faceBase64Parameter.setBase64(base64);
        Request request = new Request().setPath(Constants.UPLOAD_FACE_URL)
                .setMethod(Request.RequestMethod.POST.value())
                .setBody(faceBase64Parameter);
        return ExecutorRequest.execute(request);
    }

    @NonNull
    public static Result<FacesUrlResult> emotionAnalysis() {
        FaceUrlParameter faceUrlParameter = new FaceUrlParameter();
        faceUrlParameter.setUrl(Constants.FACE_DIRECTORY_URL + Config.getUserId() + ".jpg");
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Constants.EMOTION_KEY_CONTENT_TYPE, Constants.EMOTION_KEY_1);
        Request request = new Request().setPath(Constants.EMOTION_URL
                // https://docs.microsoft.com/en-us/azure/cognitive-services/face/face-api-how-to-topics/specify-detection-model
                + "detectionModel=" + "detection_01"
                + "&returnFaceId=" + "true"
                + "&returnFaceLandmarks=" + "false"
                + "&returnFaceAttributes=" + "age,gender,headPose,smile,facialHair,glasses,emotion,hair,"
                + "makeup,occlusion,accessories,blur,exposure,noise")
                .setHeaderMap(headers)
                .setMethod(Request.RequestMethod.POST.value())
                .setBody(faceUrlParameter);
        return ExecutorRequest.execute(request);
    }
}

