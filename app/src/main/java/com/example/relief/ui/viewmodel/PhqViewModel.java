package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.base.BaseViewModel;
import com.example.relief.model.Question;
import com.example.relief.utils.ToastUtils;

import java.util.List;

public class PhqViewModel extends BaseViewModel {

    private final MutableLiveData<List<Question>> mQuestions = new MutableLiveData<>();
    private final ObservableBoolean mEnableSubmit = new ObservableBoolean();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public MutableLiveData<List<Question>> getQuestions() {
        return mQuestions;
    }

    public ObservableBoolean getEnableSubmit() {
        return mEnableSubmit;
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
        ToastUtils.showShortSafe("Submitted");
        mIsShowLoading.postValue(false);
    }

    public void updateSubmitBtnState() {
        // 当题目未完成时
        if (mQuestions.getValue() == null) {
            return;
        }
        for (Question question : mQuestions.getValue()) {
            if (!question.isSelected()) {
                mEnableSubmit.set(false);
                break;
            }
            mEnableSubmit.set(true);
        }
    }
}
