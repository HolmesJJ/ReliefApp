package com.example.relief.init;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.MainActivity;
import com.example.relief.base.BaseViewModel;
import com.example.relief.config.Config;
import com.example.relief.ui.activity.LoginActivity;

public class InitViewModel extends BaseViewModel {

    private static final String TAG = InitViewModel.class.getSimpleName();

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

    public void initData() {
        doInitSuccess();
    }

    private void doInitSuccess() {
        if (Config.getIsLogin()) {
            mActivityAction.postValue(MainActivity.class);
        } else {
            mActivityAction.postValue(LoginActivity.class);
        }
    }
}
