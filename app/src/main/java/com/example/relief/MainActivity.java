package com.example.relief;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityMainBinding;
import com.example.relief.ui.fragment.CheckInFragment;
import com.example.relief.ui.fragment.HomeFragment;
import com.example.relief.ui.fragment.ProfileFragment;
import com.example.relief.ui.widget.BottomBar;
import com.example.relief.ui.widget.BottomBarTab;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.ListenerUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private HomeFragment mHomeFragment;
    private CheckInFragment mCheckInFragment;
    private ProfileFragment mProfileFragment;
    private Fragment[] mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<MainViewModel> getViewModelClazz() {
        return MainViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
        mHomeFragment = HomeFragment.newInstance();
        mCheckInFragment = CheckInFragment.newInstance();
        mProfileFragment = ProfileFragment.newInstance();
        mFragments = new Fragment[]{mHomeFragment, mCheckInFragment, mProfileFragment};
        addAllFragment();
        replaceFragment(mHomeFragment);
        initBottomBar();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setOnTabSelectedListener();
        doIsShowLoading();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeAllFragment();
    }

    private void initBottomBar() {
        getBinding().bottomBar
                .addItem(new BottomBarTab(ContextUtils.getContext(), R.drawable.ic_home, getString(R.string.home)))
                .addItem(new BottomBarTab(ContextUtils.getContext(), R.drawable.ic_check_in, getString(R.string.check_in)))
                .addItem(new BottomBarTab(ContextUtils.getContext(), R.drawable.ic_profile, getString(R.string.profile)));
    }

    private void addAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rl_gui_container, mHomeFragment)
                .add(R.id.rl_gui_container, mCheckInFragment)
                .add(R.id.rl_gui_container, mProfileFragment)
                .commitAllowingStateLoss();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(mHomeFragment)
                .hide(mCheckInFragment)
                .hide(mProfileFragment)
                .show(fragment)
                .commitAllowingStateLoss();
    }

    private void removeAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .remove(mHomeFragment)
                .remove(mCheckInFragment)
                .remove(mProfileFragment)
                .commitAllowingStateLoss();
    }

    private void setOnTabSelectedListener() {
        ListenerUtils.setOnTabSelectedListener(getBinding().bottomBar, new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                replaceFragment(mFragments[position]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    /**
     * 控制进度圈显示
     */
    public void doIsShowLoading() {
        getViewModel().isShowLoading().observe(this, isShowing -> {
            if (isShowing) {
                showLoading(false);
            } else {
                stopLoading();
            }
        });
    }
}
