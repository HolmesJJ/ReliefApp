package com.example.relief.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.example.relief.BR;
import com.example.relief.MainActivity;
import com.example.relief.R;
import com.example.relief.base.BaseFragment;
import com.example.relief.config.Config;
import com.example.relief.databinding.FragmentCheckInBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.CheckInViewModel;
import com.example.relief.utils.ListenerUtils;
import com.example.relief.utils.ToastUtils;

public class CheckInFragment extends BaseFragment<FragmentCheckInBinding, CheckInViewModel> {

    private static final String TAG = CheckInFragment.class.getSimpleName();

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
        updateStatus();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
        setOnClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatus();
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().civPhq, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toPhq();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().civEmotion, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toEmotion();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().civSentiment, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toSentiment();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().civMonitor, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toMonitor();
            }
        });
    }

    private void setObserveListener() {
        getViewModel().getActivityAction().observe(this, activityAction -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).stopLoading();
            }
            if (activityAction != null) {
                try {
                    Intent intent = new Intent(getActivity(), activityAction);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShortSafe(e.getMessage());
                }
            } else {
                Log.e(TAG, "activityAction is null");
            }
        });
    }

    private void updateStatus() {
        if (Config.isPhqDone()) {
            getBinding().civPhq.setCheckIn(true);
        }
        if (Config.isEmotionDone()) {
            getBinding().civEmotion.setCheckIn(true);
        }
        if (Config.isSentimentDone()) {
            getBinding().civSentiment.setCheckIn(true);
        }
        if (Config.isMonitorDone()) {
            getBinding().civMonitor.setCheckIn(true);
        }
    }
}
