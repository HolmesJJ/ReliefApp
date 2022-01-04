package com.example.relief.ui.activity;

import android.os.Bundle;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityDetailBinding;
import com.example.relief.ui.viewmodel.DetailViewModel;

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailViewModel> {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<DetailViewModel> getViewModelClazz() {
        return DetailViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
