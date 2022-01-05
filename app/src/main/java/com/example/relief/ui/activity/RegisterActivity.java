package com.example.relief.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityRegisterBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.RegisterViewModel;
import com.example.relief.utils.ListenerUtils;
import com.example.relief.utils.ToastUtils;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<RegisterViewModel> getViewModelClazz() {
        return RegisterViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
        getViewModel().getUsername().set(getBinding().etUsername.getText().toString());
        getViewModel().getPassword().set(getBinding().etPassword.getText().toString());
        getViewModel().getConfirmPassword().set(getBinding().etConfirmPassword.getText().toString());
        getViewModel().updateSignUpBtnState();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        // 设置文本变更监听
        setTextChangeListener();
        // 设置是否可注册监听，以便更改登录按钮UI
        setEnableSignUpListener();
        setObserveListener();
        setOnClickListener();
        doIsShowLoading();
    }

    @Override
    protected void onDestroy() {
        ListenerUtils.remove(getViewModel().getEnableSignUp());
        super.onDestroy();
    }

    private void setTextChangeListener() {
        // 设置账号输入文本变更监听
        ListenerUtils.addTextChangeListener(getBinding().etUsername, s -> {
            if (getViewModel() != null) {
                String value = s == null ? "" : s;
                getViewModel().getUsername().set(value);
                getViewModel().updateSignUpBtnState();
            }
        });
        // 设置密码文本变更监听
        ListenerUtils.addTextChangeListener(getBinding().etPassword, s -> {
            if (getViewModel() != null) {
                String value = s == null ? "" : s;
                getViewModel().getPassword().set(value);
                getViewModel().updateSignUpBtnState();
            }
        });
        // 设置确认密码文本变更监听
        ListenerUtils.addTextChangeListener(getBinding().etConfirmPassword, s -> {
            if (getViewModel() != null) {
                String value = s == null ? "" : s;
                getViewModel().getConfirmPassword().set(value);
                getViewModel().updateSignUpBtnState();
            }
        });
    }

    private void setEnableSignUpListener() {
        ListenerUtils.addSignalOnPropertyChangeCallback(getViewModel().getEnableSignUp(), (observable, i, value) -> {
            if (value) {
                getBinding().btnSignUp.setBackgroundResource(R.drawable.bg_btn_enable_round);
                getBinding().btnSignUp.setTextColor(getResources().getColor(R.color.white, this.getTheme()));
            } else {
                getBinding().btnSignUp.setBackgroundResource(R.drawable.bg_btn_unable_round);
                getBinding().btnSignUp.setTextColor(getResources().getColor(R.color.black, this.getTheme()));
            }
        });
    }

    private void setObserveListener() {
        getViewModel().getActivityAction().observe(this, activityAction -> {
            stopLoading();
            if (activityAction != null) {
                try {
                    Intent intent = new Intent(RegisterActivity.this, activityAction);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    ToastUtils.showShortSafe(e.getMessage());
                }
            } else {
                Log.e(TAG, "activityAction is null");
            }
        });
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().btnSignUp, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getViewModel() != null && getViewModel().getEnableSignUp().get()) {
                    getViewModel().signUp();
                }
            }
        });
        ListenerUtils.setOnClickListener(getBinding().btnSignIn, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getViewModel() != null) {
                    getViewModel().signIn();
                }
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
