package com.example.relief.ui.activity;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.adapter.phq.ComponentAdapter;
import com.example.relief.adapter.phq.QuestionAdapter;
import com.example.relief.adapter.phq.QuestionNumAdapter;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityPhqBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.model.phq.Component;
import com.example.relief.model.phq.Question;
import com.example.relief.ui.viewmodel.PhqViewModel;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.ListenerUtils;
import com.example.relief.utils.QuestionUtils;

import java.util.List;

public class PhqActivity extends BaseActivity<ActivityPhqBinding, PhqViewModel> {

    private static final String TAG = PhqActivity.class.getSimpleName();

    private QuestionNumAdapter mQuestionNumAdapter;
    private QuestionAdapter mQuestionAdapter;
    private List<Question> mQuestions;

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
        mQuestions = QuestionUtils.getDefaultMenus();
        initQuestionNums();
        initQuestions();
        getViewModel().updateSubmitBtnState();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
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

    private void initQuestionNums() {
        mQuestionNumAdapter = new QuestionNumAdapter(ContextUtils.getContext(), mQuestions, new QuestionNumAdapter.OnItemListener() {
            @Override
            public void onItemListener(int position) {
                smoothScrollToPosition(position);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        getBinding().rvQuestionNums.setLayoutManager(layoutManager);
        getBinding().rvQuestionNums.setAdapter(mQuestionNumAdapter);
    }

    private void initQuestions() {
        mQuestionAdapter = new QuestionAdapter(ContextUtils.getContext(), mQuestions, new QuestionAdapter.OnItemListener() {
            @Override
            public void onItemListener(int position) {
                mQuestionNumAdapter.notifyItemChanged(position);
                getViewModel().getQuestions().setValue(mQuestions);
                getViewModel().updateSubmitBtnState();
            }
        });
        LinearLayoutManager mQuestionManager = new LinearLayoutManager(this);
        mQuestionManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().rvQuestions.setLayoutManager(mQuestionManager);
        getBinding().rvQuestions.setAdapter(mQuestionAdapter);
    }

    private void smoothScrollToPosition(int position) {
        getBinding().rvQuestions.scrollToPosition(position);
        LinearLayoutManager layoutManager = (LinearLayoutManager) getBinding().rvQuestions.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.scrollToPositionWithOffset(position, 0);
        }
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
                resetQuestion();
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

    private void resetQuestion() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mQuestions.size(); i++) {
                    Question question = mQuestions.get(i);
                    question.setSelected(false);
                    mQuestionNumAdapter.notifyItemChanged(i);
                    for (int j = 0; j < question.getComponents().size(); j++) {
                        Component component = question.getComponents().get(j);
                        component.setSelected(false);
                        ComponentAdapter componentAdapter = mQuestionAdapter.getComponentAdapter();
                        if (componentAdapter != null) {
                            componentAdapter.notifyItemChanged(j);
                        }
                    }
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
