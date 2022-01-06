package com.example.relief.ui.viewmodel;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSON;
import com.example.relief.api.ApiClient;
import com.example.relief.api.model.ocr.OcrResult;
import com.example.relief.api.model.ocr.WordResult;
import com.example.relief.api.model.sentiment.AssessmentResult;
import com.example.relief.api.model.sentiment.DocumentResult;
import com.example.relief.api.model.sentiment.SentenceResult;
import com.example.relief.api.model.sentiment.SentimentEnum;
import com.example.relief.api.model.sentiment.SentimentResult;
import com.example.relief.api.model.sentiment.TargetResult;
import com.example.relief.base.BaseViewModel;
import com.example.relief.callback.OnTaskCompleted;
import com.example.relief.config.Config;
import com.example.relief.constants.Constants;
import com.example.relief.network.http.HttpAsyncTaskPost;
import com.example.relief.network.http.Result;
import com.example.relief.thread.ThreadManager;
import com.example.relief.utils.Base64Utils;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.FileUtils;
import com.example.relief.utils.ToastUtils;

import java.io.InputStream;
import java.net.URLEncoder;

public class SentimentViewModel extends BaseViewModel implements OnTaskCompleted {

    private static final String TAG = SentimentViewModel.class.getSimpleName();
    private static final int OCR_REQUEST_ID = 100;

    private final MutableLiveData<Uri> mImageUri = new MutableLiveData<>();
    private final MutableLiveData<String> mContent = new MutableLiveData<>();
    private final MutableLiveData<String> mSentiment = new MutableLiveData<>();
    private final MutableLiveData<String> mTarget = new MutableLiveData<>();
    private final MutableLiveData<String> mKeyword = new MutableLiveData<>();
    private final MutableLiveData<String> mSuggestion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onTaskCompleted(String response, int requestId) {
        OcrResult result = JSON.parseObject(response, OcrResult.class);
        StringBuilder content = new StringBuilder();
        for (WordResult wordResult : result.getWordsResult()) {
            content.append(wordResult.getWords()).append(" ");
        }
        Log.i(TAG, "OCR: " + content);
        if (!TextUtils.isEmpty(content.toString())) {
            mContent.postValue(content.toString());
            analysis(content.toString());
        } else {
            isShowLoading().postValue(false);
            ToastUtils.showShortSafe("Empty content");
        }
    }

    public MutableLiveData<Uri> getImageUri() {
        return mImageUri;
    }

    public MutableLiveData<String> getContent() {
        return mContent;
    }

    public MutableLiveData<String> getSentiment() {
        return mSentiment;
    }

    public MutableLiveData<String> getTarget() {
        return mTarget;
    }

    public MutableLiveData<String> getKeyword() {
        return mKeyword;
    }

    public MutableLiveData<String> getSuggestion() {
        return mSuggestion;
    }

    public MutableLiveData<Boolean> isShowLoading() {
        return mIsShowLoading;
    }

    public void upload() {
        if (mImageUri.getValue() == null) {
            return;
        }
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = ContextUtils.getContext().getContentResolver().openInputStream(mImageUri.getValue());
                    byte[] imgData = FileUtils.getInputStreamBytes(inputStream);
                    String imgStr = Base64Utils.encode(imgData);
                    String imgParam = URLEncoder.encode(imgStr, "UTF-8");
                    String param = "image=" + imgParam;
                    HttpAsyncTaskPost post = new HttpAsyncTaskPost(SentimentViewModel.this, OCR_REQUEST_ID);
                    post.execute(Constants.OCR_STANDARD_URL + "access_token=" + Config.getOcrAccessToken(), param);
                } catch (Exception e) {
                    isShowLoading().postValue(false);
                    ToastUtils.showShortSafe("OCR Error");
                }
            }
        });
    }

    public void analysis(String content) {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                Result<SentimentResult> result = ApiClient.sentimentAnalysis(content);
                if (result.isSuccess()) {
                    SentimentResult body = result.getBody(SentimentResult.class);
                    Log.i(TAG, "Sentiment: " + body);
                    if (body.getDocuments().size() > 0) {
                        DocumentResult documentResult = body.getDocuments().get(0);
                        String sentiment = documentResult.getSentiment();
                        mSentiment.postValue(sentiment);
                        if (documentResult.getSentences().size() > 0) {
                            SentenceResult sentimentResult = documentResult.getSentences().get(0);
                            if (sentimentResult.getTargets().size() > 0) {
                                TargetResult targetResult = sentimentResult.getTargets().get(0);
                                mTarget.postValue(targetResult.getText());
                                AssessmentResult assessmentResult = sentimentResult.getAssessments().get(0);
                                mKeyword.postValue(assessmentResult.getText());
                            }
                        }
                        if (SentimentEnum.POSITIVE.getValue().equals(sentiment)) {
                            mSuggestion.postValue("More smiling, less worrying. More compassion, less judgment. "
                                    + "More blessed, less stressed. More love, less hate.");
                        } else if (SentimentEnum.NEUTRAL.getValue().equals(sentiment)) {
                            mSuggestion.postValue("Believe in yourself. You are braver than you think, "
                                    + "more talented than you know, and capable of more than you imagine.");
                        } else if (SentimentEnum.NEGATIVE.getValue().equals(sentiment)) {
                            mSuggestion.postValue("There is hope, even when your brain tells you there isn’t.");
                        }
                    }
                    Config.setSentimentDone(true);
                    isShowLoading().postValue(false);
                } else {
                    isShowLoading().postValue(false);
                    ToastUtils.showShortSafe("Sentiment Analytics Error");
                }
            }
        });
    }
}
