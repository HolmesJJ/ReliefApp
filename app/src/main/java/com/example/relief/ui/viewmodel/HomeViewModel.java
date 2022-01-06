package com.example.relief.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.example.relief.base.BaseViewModel;
import com.example.relief.ui.activity.DetailActivity;

public class HomeViewModel extends BaseViewModel {

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

    public void toDetail() {
        mActivityAction.postValue(DetailActivity.class);
    }
}
