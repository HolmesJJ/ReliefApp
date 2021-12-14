package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.MainActivity;
import com.example.relief.base.BaseViewModel;
import com.example.relief.ui.activity.RegisterActivity;

public class LoginViewModel extends BaseViewModel {

    private final MutableLiveData<Class> mActivityAction = new MutableLiveData<>();
    private final ObservableField<String> mUsername = new ObservableField<>();
    private final ObservableField<String> mPassword = new ObservableField<>();
    private final ObservableBoolean mEnableSignIn = new ObservableBoolean();

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

    public ObservableBoolean getEnableSignIn() {
        return mEnableSignIn;
    }

    public void signIn() {
        // 不允许登录
        if (!mEnableSignIn.get()) {
            return;
        }
        mActivityAction.postValue(MainActivity.class);
    }

    public void signUp() {
        mActivityAction.postValue(RegisterActivity.class);
    }

    public void updateSignInBtnState() {
        // 当账号或者密码为空的时候不允许登录
        String username = mUsername.get();
        String password = mPassword.get();
        mEnableSignIn.set(username != null && !"".equals(username) && password != null && !"".equals(password));
    }
}
