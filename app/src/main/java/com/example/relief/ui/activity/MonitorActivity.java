package com.example.relief.ui.activity;

import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityMonitorBinding;
import com.example.relief.ui.viewmodel.MonitorViewModel;

public class MonitorActivity extends BaseActivity<ActivityMonitorBinding, MonitorViewModel> {

    private static final String TAG = MonitorActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_monitor;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<MonitorViewModel> getViewModelClazz() {
        return MonitorViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
