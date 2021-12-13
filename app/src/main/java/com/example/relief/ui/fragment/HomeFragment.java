package com.example.relief.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseFragment;
import com.example.relief.databinding.FragmentHomeBinding;
import com.example.relief.ui.viewmodel.HomeViewModel;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<HomeViewModel> getViewModelClazz() {
        return HomeViewModel.class;
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        super.initData();
    }
}
