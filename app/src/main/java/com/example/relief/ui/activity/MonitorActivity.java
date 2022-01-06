package com.example.relief.ui.activity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityMonitorBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.MonitorViewModel;
import com.example.relief.utils.ListenerUtils;

public class MonitorActivity extends BaseActivity<ActivityMonitorBinding, MonitorViewModel> {

    private static final String TAG = MonitorActivity.class.getSimpleName();

    private static class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

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
        getBinding().etSleepQualityScore.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        getBinding().etHeartRateScore.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        getViewModel().updateSubmitBtnState();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setTextChangeListener();
        // 设置是否可提交监听，以便更改登录按钮UI
        setEnableSubmitListener();
        setObserveListener();
        setOnClickListener();
        doIsShowLoading();
    }

    @Override
    protected void onDestroy() {
        ListenerUtils.remove(getViewModel().getEnableSubmit());
        super.onDestroy();
    }

    private void setTextChangeListener() {
        ListenerUtils.addTextChangeListener(getBinding().etSleepQualityScore, s -> {
            if (getViewModel() != null) {
                String value = s == null ? "" : s;
                getViewModel().getSleepQualityScore().set(value);
                getViewModel().updateSubmitBtnState();
            }
        });
        ListenerUtils.addTextChangeListener(getBinding().etHeartRateScore, s -> {
            if (getViewModel() != null) {
                String value = s == null ? "" : s;
                getViewModel().getHeartRateScore().set(value);
                getViewModel().updateSubmitBtnState();
            }
        });
    }

    private void setEnableSubmitListener() {
        ListenerUtils.addSignalOnPropertyChangeCallback(getViewModel().getEnableSubmit(), (observable, i, value) -> {
            if (value) {
                getBinding().btnSubmit.setBackgroundResource(R.drawable.bg_btn_enable_round);
                getBinding().btnSubmit.setTextColor(getResources().getColor(R.color.white, this.getTheme()));
            } else {
                getBinding().btnSubmit.setBackgroundResource(R.drawable.bg_btn_unable_round);
                getBinding().btnSubmit.setTextColor(getResources().getColor(R.color.black, this.getTheme()));
            }
        });
    }

    private void setObserveListener() {
        getViewModel().isSubmitted().observe(this, isSubmitted -> {
            if (isSubmitted) {
                getBinding().etSleepQualityScore.setText("");
                getBinding().etHeartRateScore.setText("");
                getBinding().sStressType.setSelection(0);
                getBinding().sStressLevel.setSelection(0);
            }
        });
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().btnSubmit, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                getViewModel().submit();
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
