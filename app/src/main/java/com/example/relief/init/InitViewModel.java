package com.example.relief.init;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.example.relief.MainActivity;
import com.example.relief.base.BaseViewModel;

public class InitViewModel extends BaseViewModel {

    private static final String TAG = InitViewModel.class.getSimpleName();

    private final MutableLiveData<Class> mActivityAction = new MutableLiveData<>();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {

    }

    public void initData() {
        doInitSuccess();
    }

    private void doInitSuccess() {
        mActivityAction.postValue(MainActivity.class);
    }

    public MutableLiveData<Class> getActivityAction() {
        return mActivityAction;
    }
}
