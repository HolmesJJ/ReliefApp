package com.example.relief.adapter.chatbot;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.relief.R;
import com.example.relief.adapter.BaseAdapter;
import com.example.relief.adapter.ViewHolder;
import com.example.relief.model.chatbot.Chat;

import java.util.List;

public class ChatAdapter extends BaseAdapter<Chat> {

    public ChatAdapter(Context context, List<Chat> chats) {
        super(context, chats);
    }

    @Override
    public void onBindContentViews(RecyclerView.ViewHolder holder, int position) {
        Chat chat = getData().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setText(R.id.tv_message, chat.getContent());
    }

    @Override
    public RecyclerView.ViewHolder initContentViews(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_chat_send);
        }
        return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_chat_receive);
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }
}
