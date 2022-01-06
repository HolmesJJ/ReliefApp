package com.example.relief.init;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.MainActivity;
import com.example.relief.api.ApiClient;
import com.example.relief.api.model.ocr.AccessTokenResult;
import com.example.relief.base.BaseViewModel;
import com.example.relief.config.Config;
import com.example.relief.network.http.Result;
import com.example.relief.thread.ThreadManager;
import com.example.relief.ui.activity.LoginActivity;
import com.example.relief.utils.ToastUtils;

public class InitViewModel extends BaseViewModel {

    private static final String TAG = InitViewModel.class.getSimpleName();

    private final MutableLiveData<Class> mActivityAction = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsInitSuccess = new MutableLiveData<>();

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    public MutableLiveData<Class> getActivityAction() {
        return mActivityAction;
    }

    public MutableLiveData<Boolean> isInitSuccess() {
        return mIsInitSuccess;
    }

    public void initData() {
        doInitSuccess();
    }

    private void doInitSuccess() {
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                Result<AccessTokenResult> result = ApiClient.getOcrAccessToken();
                if (result.isSuccess()) {
                    AccessTokenResult body = result.getBody(AccessTokenResult.class);
                    Config.setOcrAccessToken(body.getAccessToken());
                    Config.setOcrExpireTime(System.currentTimeMillis() + body.getExpiresIn());
                    Config.setOcrRefreshToken(body.getRefreshToken());
                    Log.i(TAG, "Access Token: " + body.getAccessToken());
                    mIsInitSuccess.postValue(true);
                    if (Config.isLogin()) {
                        mActivityAction.postValue(MainActivity.class);
                    } else {
                        mActivityAction.postValue(LoginActivity.class);
                    }
                } else {
                    mIsInitSuccess.postValue(false);
                    ToastUtils.showShortSafe("OCR Token is Invalid");
                }
            }
        });
    }
}
