package com.example.relief.ui.viewmodel;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.api.ApiClient;
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

public class SentimentViewModel extends BaseViewModel {

    private static final String TAG = SentimentViewModel.class.getSimpleName();

    private final MutableLiveData<Uri> mImageUri = new MutableLiveData<>();
    private final MutableLiveData<String> mContent = new MutableLiveData<>();
    private final MutableLiveData<String> mSentiment = new MutableLiveData<>();
    private final MutableLiveData<String> mTarget = new MutableLiveData<>();
    private final MutableLiveData<String> mKeyword = new MutableLiveData<>();
    private final MutableLiveData<String> mSuggestion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    private final HttpAsyncTaskPost post = new HttpAsyncTaskPost(new OnTaskCompleted() {
        @Override
        public void onTaskCompleted(String response, int requestId) {
            System.out.println("OCR: " + response);
        }
    }, 105);

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

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
                    post.execute(Constants.OCR_WEB_IMAGE_URL + "access_token=" + Config.getOcrAccessToken(), param);
//                    Result<OcrResult> result = ApiClient.ocr(param);
//                    if (result.isSuccess()) {
//                        OcrResult body = result.getBody(OcrResult.class);
//                        Log.i(TAG, "OCR: " + body);
//                    }
                    mContent.postValue("The exam is fucking hard.");
                    analysis("The exam is fucking hard.");
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
                    isShowLoading().postValue(false);
                } else {
                    isShowLoading().postValue(false);
                    ToastUtils.showShortSafe("Sentiment Analytics Error");
                }
            }
        });
    }
}
