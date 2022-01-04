package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.MainActivity;
import com.example.relief.base.BaseViewModel;
import com.example.relief.ui.activity.LoginActivity;

public class RegisterViewModel extends BaseViewModel {

    private final MutableLiveData<Class> mActivityAction = new MutableLiveData<>();
    private final ObservableField<String> mUsername = new ObservableField<>();
    private final ObservableField<String> mPassword = new ObservableField<>();
    private final ObservableField<String> mConfirmPassword = new ObservableField<>();
    private final ObservableBoolean mEnableSignUp = new ObservableBoolean();
    private final MutableLiveData<Boolean> mIsShowLoading = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public MutableLiveData<Class> getActivityAction() {
        return mActivityAction;
    }

    public ObservableField<String> getUsername() {
        return mUsername;
    }

    public ObservableField<String> getPassword() {
        return mPassword;
    }

    public ObservableField<String> getConfirmPassword() {
        return mConfirmPassword;
    }

    public ObservableBoolean getEnableSignUp() {
        return mEnableSignUp;
    }

    public MutableLiveData<Boolean> isShowLoading() {
        return mIsShowLoading;
    }

    public void signUp() {
        // 不允许注册
        if (!mEnableSignUp.get()) {
            return;
        }
        mIsShowLoading.postValue(true);
        mActivityAction.postValue(MainActivity.class);
    }

    public void signIn() {
        mIsShowLoading.postValue(true);
        mActivityAction.postValue(LoginActivity.class);
    }

    public void updateSignUpBtnState() {
        // 当账号或者密码为空的时候不允许注册
        // 当两次输入密码不同的时候不允许注册
        String username = mUsername.get();
        String password = mPassword.get();
        String confirmPassword = mConfirmPassword.get();
        mEnableSignUp.set(username != null && !"".equals(username)
                && password != null && !"".equals(password)
                && confirmPassword != null && !"".equals(confirmPassword)
                && password.equals(confirmPassword));
    }
}
