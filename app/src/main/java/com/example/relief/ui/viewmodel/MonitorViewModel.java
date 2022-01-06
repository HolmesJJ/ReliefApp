package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.base.BaseViewModel;
import com.example.relief.config.Config;
import com.example.relief.thread.ThreadManager;
import com.example.relief.utils.ToastUtils;

public class MonitorViewModel extends BaseViewModel {

    private final ObservableBoolean mEnableSubmit = new ObservableBoolean();
    private final ObservableField<String> mSleepQualityScore = new ObservableField<>();
    private final ObservableField<String> mHeartRateScore = new ObservableField<>();
    private final MutableLiveData<Boolean> mIsSubmitted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public ObservableBoolean getEnableSubmit() {
        return mEnableSubmit;
    }

    public ObservableField<String> getSleepQualityScore() {
        return mSleepQualityScore;
    }

    public ObservableField<String> getHeartRateScore() {
        return mHeartRateScore;
    }

    public MutableLiveData<Boolean> isSubmitted() {
        return mIsSubmitted;
    }

    public MutableLiveData<Boolean> isShowLoading() {
        return mIsShowLoading;
    }

    public void submit() {
        // 不允许提交
        if (!mEnableSubmit.get()) {
            return;
        }
        mIsShowLoading.postValue(true);
        // 模拟提交
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
                Config.setMonitorDone(true);
                ToastUtils.showShortSafe("Submitted");
                mIsSubmitted.postValue(true);
                mIsShowLoading.postValue(false);
            }
        });
    }

    public void updateSubmitBtnState() {
        mEnableSubmit.set(true);
        String sleepQualityScore = mSleepQualityScore.get();
        String heartRateScore = mHeartRateScore.get();
        mEnableSubmit.set(sleepQualityScore != null && !"".equals(sleepQualityScore)
                && heartRateScore != null && !"".equals(heartRateScore));
    }
}
