package com.example.relief.ui.viewmodel;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.api.ApiClient;
import com.example.relief.api.model.chatbot.AnswerResult;
import com.example.relief.api.model.chatbot.AnswersResult;
import com.example.relief.base.BaseViewModel;
import com.example.relief.network.http.Result;
import com.example.relief.thread.ThreadManager;

import java.util.List;

public class ChatbotViewModel extends BaseViewModel {

    private static final String TAG = ChatbotViewModel.class.getSimpleName();

    private final MutableLiveData<String> mReceivedMessage = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public MutableLiveData<String> getReceivedMessage() {
        return mReceivedMessage;
    }

    public void send(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                Result<AnswersResult> result = ApiClient.getAnswer(message);
                if (result.isSuccess()) {
                    AnswersResult body = result.getBody(AnswersResult.class);
                    Log.i(TAG, "Received Message: " + body);
                    List<AnswerResult> answers = body.getAnswers();
                    if (answers != null && answers.size() > 0) {
                        int id = answers.get(0).getId();
                        if (id > 0) {
                            String answer = answers.get(0).getAnswer();
                            mReceivedMessage.postValue(answer);
                        }
                    }
                }
            }
        });
    }
}
