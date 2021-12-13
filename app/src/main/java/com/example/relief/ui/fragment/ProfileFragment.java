package com.example.relief.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseFragment;
import com.example.relief.databinding.FragmentProfileBinding;
import com.example.relief.ui.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_profile;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<ProfileViewModel> getViewModelClazz() {
        return ProfileViewModel.class;
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
