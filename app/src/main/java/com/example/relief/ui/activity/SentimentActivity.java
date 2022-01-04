package com.example.relief.ui.activity;

import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivitySentimentBinding;
import com.example.relief.ui.viewmodel.SentimentViewModel;

public class SentimentActivity extends BaseActivity<ActivitySentimentBinding, SentimentViewModel> {

    private static final String TAG = SentimentActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sentiment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<SentimentViewModel> getViewModelClazz() {
        return SentimentViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
