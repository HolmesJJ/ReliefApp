package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.base.BaseViewModel;
import com.example.relief.ui.activity.EmotionActivity;
import com.example.relief.ui.activity.MonitorActivity;
import com.example.relief.ui.activity.PhqActivity;
import com.example.relief.ui.activity.SentimentActivity;

public class CheckInViewModel extends BaseViewModel {

    private final MutableLiveData<Class> mActivityAction = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public MutableLiveData<Class> getActivityAction() {
        return mActivityAction;
    }

    public void toPhq() {
        mActivityAction.postValue(PhqActivity.class);
    }

    public void toEmotion() {
        mActivityAction.postValue(EmotionActivity.class);
    }

    public void toSentiment() {
        mActivityAction.postValue(SentimentActivity.class);
    }

    public void toMonitor() {
        mActivityAction.postValue(MonitorActivity.class);
    }
}
