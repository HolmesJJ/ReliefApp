package com.example.relief.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseFragment;
import com.example.relief.databinding.FragmentCheckInBinding;
import com.example.relief.ui.viewmodel.CheckInViewModel;

public class CheckInFragment extends BaseFragment<FragmentCheckInBinding, CheckInViewModel> {

    public static CheckInFragment newInstance() {
        return new CheckInFragment();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check_in;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<CheckInViewModel> getViewModelClazz() {
        return CheckInViewModel.class;
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
