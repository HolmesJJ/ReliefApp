package com.example.relief.ui.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.api.ApiClient;
import com.example.relief.api.model.emotion.EmotionEnum;
import com.example.relief.api.model.emotion.FaceAttributesResult;
import com.example.relief.api.model.emotion.FaceBase64Result;
import com.example.relief.api.model.emotion.FaceUrlResult;
import com.example.relief.api.model.emotion.FacesUrlResult;
import com.example.relief.base.BaseViewModel;
import com.example.relief.network.http.Result;
import com.example.relief.thread.ThreadManager;
import com.example.relief.utils.BitmapUtils;
import com.example.relief.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

public class EmotionViewModel extends BaseViewModel {

    private static final String TAG = EmotionViewModel.class.getSimpleName();

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
                        analysis();
                    } else {
                        mIsAnalysing.postValue(false);
                    }
                } else {
                    mIsAnalysing.postValue(false);
                }
            }
        });
    }

    public void analysis() {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                Result<FacesUrlResult> result = ApiClient.emotionAnalysis();
                if (result.isSuccess()) {
                    FacesUrlResult body = result.getBody(FacesUrlResult.class);
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
                            mSuggestion.postValue("ANGER");
                        } else if (EmotionEnum.CONTEMPT.getValue().equals(emotion)) {
                            mSuggestion.postValue("CONTEMPT");
                        } else if (EmotionEnum.DISGUST.getValue().equals(emotion)) {
                            mSuggestion.postValue("DISGUST");
                        } else if (EmotionEnum.FEAR.getValue().equals(emotion)) {
                            mSuggestion.postValue("FEAR");
                        } else if (EmotionEnum.HAPPINESS.getValue().equals(emotion)) {
                            mSuggestion.postValue("HAPPINESS");
                        } else if (EmotionEnum.NEUTRAL.getValue().equals(emotion)) {
                            mSuggestion.postValue("NEUTRAL");
                        } else if (EmotionEnum.SADNESS.getValue().equals(emotion)) {
                            mSuggestion.postValue("SADNESS");
                        } else if (EmotionEnum.SURPRISE.getValue().equals(emotion)) {
                            mSuggestion.postValue("SURPRISE");
                        }
                    }
                }
                mIsAnalysing.postValue(false);
            }
        });
    }
}
