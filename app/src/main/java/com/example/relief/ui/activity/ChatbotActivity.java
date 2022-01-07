package com.example.relief.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.adapter.chatbot.ChatAdapter;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityChatbotBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.model.chatbot.Chat;
import com.example.relief.ui.viewmodel.ChatbotViewModel;
import com.example.relief.utils.ChatUtils;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.ListenerUtils;

import java.util.List;

public class ChatbotActivity extends BaseActivity<ActivityChatbotBinding, ChatbotViewModel> {

    private static final String TAG = ChatbotActivity.class.getSimpleName();

    private ChatAdapter chatAdapter;
    private List<Chat> chats;

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
        initChat();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
        setOnClickListener();
    }

    private void setObserveListener() {
        getViewModel().getReceivedMessage().observe(this, receivedMessage -> {
            if (!TextUtils.isEmpty(receivedMessage) && chats != null) {
                chats.add(new Chat(receivedMessage, 1));
                chatAdapter.notifyItemInserted(chats.size() - 1);
                getBinding().rvChats.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().btnSend, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                String message = getBinding().etMessage.getText().toString();
                if (!TextUtils.isEmpty(message) && chats != null) {
                    chats.add(new Chat(message, 0));
                    chatAdapter.notifyItemInserted(chats.size() - 1);
                    getBinding().rvChats.scrollToPosition(chatAdapter.getItemCount() - 1);
                    getViewModel().send(message);
                    getBinding().etMessage.setText("");
                }
            }
        });
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
    }

    private void initChat() {
        chats = ChatUtils.getDefaultMenus();
        if (chats == null || chats.size() == 0) {
            return;
        }
        chatAdapter = new ChatAdapter(ContextUtils.getContext(), chats);
        getBinding().rvChats.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().rvChats.setLayoutManager(layoutManager);
    }
}
