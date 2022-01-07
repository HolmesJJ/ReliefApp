package com.example.relief.ui.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSON;
import com.example.relief.api.ApiClient;
import com.example.relief.api.model.emotion.EmotionEnum;
import com.example.relief.api.model.emotion.FaceAttributesResult;
import com.example.relief.api.model.emotion.FaceBase64Result;
import com.example.relief.api.model.emotion.FaceUrlResult;
import com.example.relief.api.model.emotion.FacesUrlResult;
import com.example.relief.base.BaseViewModel;
import com.example.relief.callback.OnTaskCompleted;
import com.example.relief.config.Config;
import com.example.relief.constants.Constants;
import com.example.relief.network.http.HttpAsyncTaskPost;
import com.example.relief.network.http.Result;
import com.example.relief.thread.ThreadManager;
import com.example.relief.utils.BitmapUtils;
import com.example.relief.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

public class EmotionViewModel extends BaseViewModel implements OnTaskCompleted {

    private static final String TAG = EmotionViewModel.class.getSimpleName();
    private static final int EMOTION_REQUEST_ID = 100;

    private final MutableLiveData<Bitmap> mFaceBitmap = new MutableLiveData<>();
    private final MutableLiveData<String> mEmotion = new MutableLiveData<>();
    private final MutableLiveData<Double> mSmile = new MutableLiveData<>();
    private final MutableLiveData<String> mSuggestion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsAnalysing = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onTaskCompleted(String response, int requestId) {
        Log.i(TAG, "Emotion: " + response);
        String resultStr = "{" + "\"list\":" + response + "}";
        FacesUrlResult body = JSON.parseObject(resultStr, FacesUrlResult.class);
        process(body);
        Config.setEmotionDone(true);
        mIsAnalysing.postValue(false);
    }

    public MutableLiveData<Bitmap> getFaceBitmap() {
        return mFaceBitmap;
    }

    public MutableLiveData<String> getEmotion() {
        return mEmotion;
    }

    public MutableLiveData<Double> getSmile() {
        return mSmile;
    }

    public MutableLiveData<String> getSuggestion() {
        return mSuggestion;
    }

    public MutableLiveData<Boolean> isAnalysing() {
        return mIsAnalysing;
    }

    public MutableLiveData<Boolean> isShowLoading() {
        return mIsShowLoading;
    }

    public void analysisByStream() {
        if (mFaceBitmap.getValue() == null) {
            return;
        }
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                byte[] data = BitmapUtils.btmToBytes(mFaceBitmap.getValue());
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/octet-stream");
                headers.put(Constants.EMOTION_KEY_CONTENT_TYPE, Constants.EMOTION_KEY_1);
                HttpAsyncTaskPost post = new HttpAsyncTaskPost(EmotionViewModel.this, EMOTION_REQUEST_ID);
                post.execute(Constants.EMOTION_URL
                        // https://docs.microsoft.com/en-us/azure/cognitive-services/face/face-api-how-to-topics/specify-detection-model
                        + "detectionModel=" + "detection_01"
                        + "&returnFaceId=" + "true"
                        + "&returnFaceLandmarks=" + "false"
                        + "&returnFaceAttributes=" + "age,gender,headPose,smile,facialHair,glasses,emotion,hair,"
                        + "makeup,occlusion,accessories,blur,exposure,noise", data, headers);
            }
        });
    }

    public void uploadFace() {
        if (mFaceBitmap.getValue() == null) {
            return;
        }
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                String base64 = BitmapUtils.convertBitmapToBase64(mFaceBitmap.getValue());
                Result<FaceBase64Result> result = ApiClient.uploadFace(base64);
                if (result.isSuccess()) {
                    FaceBase64Result body = result.getBody(FaceBase64Result.class);
                    Log.i(TAG, "Upload Face: " + body);
                    if (body.getCode() == 0) {
                        analysisByUrl();
                    } else {
                        mIsAnalysing.postValue(false);
                    }
                } else {
                    mIsAnalysing.postValue(false);
                }
            }
        });
    }

    public void analysisByUrl() {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                Result<FacesUrlResult> result = ApiClient.emotionAnalysis();
                if (result.isSuccess()) {
                    FacesUrlResult body = result.getBody(FacesUrlResult.class);
                    Log.i(TAG, "Emotion: " + body);
                    process(body);
                }
                mIsAnalysing.postValue(false);
            }
        });
    }

    private void process(FacesUrlResult body) {
        Log.i(TAG, "Emotion: " + body);
        if (body != null && body.getList().size() > 0) {
            FaceUrlResult faceUrlResult = body.getList().get(0);
            FaceAttributesResult faceAttributesResult = faceUrlResult.getFaceAttributes();
            mSmile.postValue(faceAttributesResult.getSmile());
            Map<String, Double> emotions = new HashMap<>();
            emotions.put(EmotionEnum.ANGER.getValue(), faceAttributesResult.getEmotion().getAnger());
            emotions.put(EmotionEnum.CONTEMPT.getValue(), faceAttributesResult.getEmotion().getContempt());
            emotions.put(EmotionEnum.DISGUST.getValue(), faceAttributesResult.getEmotion().getDisgust());
            emotions.put(EmotionEnum.FEAR.getValue(), faceAttributesResult.getEmotion().getFear());
            emotions.put(EmotionEnum.HAPPINESS.getValue(), faceAttributesResult.getEmotion().getHappiness());
            emotions.put(EmotionEnum.NEUTRAL.getValue(), faceAttributesResult.getEmotion().getNeutral());
            emotions.put(EmotionEnum.SADNESS.getValue(), faceAttributesResult.getEmotion().getSadness());
            emotions.put(EmotionEnum.SURPRISE.getValue(), faceAttributesResult.getEmotion().getSurprise());
            Map<String, Double> sortedEmotions = MapUtils.sortByValue(emotions);
            Map.Entry<String, Double> entry = sortedEmotions.entrySet().iterator().next();
            String emotion = entry.getKey();
            mEmotion.postValue(emotion);
            if (EmotionEnum.ANGER.getValue().equals(emotion)) {
                mSuggestion.postValue("Don't get angry quickly - be kind, be forgiving, be patient.");
            } else if (EmotionEnum.CONTEMPT.getValue().equals(emotion)) {
                mSuggestion.postValue("You can spit on a rose, but it's still a rose.");
            } else if (EmotionEnum.DISGUST.getValue().equals(emotion)) {
                mSuggestion.postValue("It’s okay to walk out of someone’s life "
                        + "if your don’t feel like you belong in it anymore.");
            } else if (EmotionEnum.FEAR.getValue().equals(emotion)) {
                mSuggestion.postValue("Successful people don’t fear failure, "
                        + "but understand that it’s necessary to learn and grow from.");
            } else if (EmotionEnum.HAPPINESS.getValue().equals(emotion)) {
                mSuggestion.postValue("Let us be grateful to the people who make us happy; "
                        + "they are the charming gardeners who make our souls blossom.");
            } else if (EmotionEnum.NEUTRAL.getValue().equals(emotion)) {
                mSuggestion.postValue("Believe you can and you're halfway there.");
            } else if (EmotionEnum.SADNESS.getValue().equals(emotion)) {
                mSuggestion.postValue("Don’t be sad, tomorrow and forever, we will be together.");
            } else if (EmotionEnum.SURPRISE.getValue().equals(emotion)) {
                mSuggestion.postValue("Sometimes the most shocking surprises are also the most beautiful surprises.");
            }
        }
    }
}
