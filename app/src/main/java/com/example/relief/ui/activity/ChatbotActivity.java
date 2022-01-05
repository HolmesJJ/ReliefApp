package com.example.relief.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityChatbotBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.ChatbotViewModel;
import com.example.relief.utils.ListenerUtils;

public class ChatbotActivity extends BaseActivity<ActivityChatbotBinding, ChatbotViewModel> {

    private static final String TAG = ChatbotActivity.class.getSimpleName();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chatbot;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<ChatbotViewModel> getViewModelClazz() {
        return ChatbotViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setOnClickListener();
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
    }
}
