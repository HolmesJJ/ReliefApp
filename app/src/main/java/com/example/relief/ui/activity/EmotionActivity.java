package com.example.relief.ui.activity;

import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityEmotionBinding;
import com.example.relief.ui.viewmodel.EmotionViewModel;

public class EmotionActivity extends BaseActivity<ActivityEmotionBinding, EmotionViewModel> {

    private static final String TAG = EmotionActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_emotion;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<EmotionViewModel> getViewModelClazz() {
        return EmotionViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
