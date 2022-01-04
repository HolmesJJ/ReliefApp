package com.example.relief.ui.activity;

import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityPhqBinding;
import com.example.relief.ui.viewmodel.PhqViewModel;

public class PhqActivity extends BaseActivity<ActivityPhqBinding, PhqViewModel> {

    private static final String TAG = PhqActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_phq;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<PhqViewModel> getViewModelClazz() {
        return PhqViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
