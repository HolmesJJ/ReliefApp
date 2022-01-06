package com.example.relief.adapter.phq;

import android.content.Context;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.relief.R;
import com.example.relief.adapter.BaseAdapter;
import com.example.relief.adapter.ViewHolder;
import com.example.relief.model.phq.Question;

import java.util.List;

public class QuestionAdapter extends BaseAdapter<Question> {

    private final OnItemListener onItemListener;

    private ComponentAdapter componentAdapter;

    public QuestionAdapter(Context context, List<Question> questions, OnItemListener onItemListener) {
        super(context, questions);
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindContentViews(RecyclerView.ViewHolder holder, int position) {
        Question question = getData().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setText(R.id.tv_title, position + 1 + ". " + question.getTitle());

        RecyclerView components = viewHolder.getView(R.id.rv_components);
        components.setItemViewCacheSize(200);
        components.setDrawingCacheEnabled(true);
        componentAdapter = new ComponentAdapter(getContext(), question.getComponents(), new ComponentAdapter.OnItemListener() {
            @Override
            public void onItemListener() {
                getData().get(position).setSelected(true);
                if (onItemListener != null) {
                    onItemListener.onItemListener(position);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        components.setLayoutManager(linearLayoutManager);
        components.setAdapter(componentAdapter);
    }

    @Override
    public RecyclerView.ViewHolder initContentViews(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_question);
    }

    public ComponentAdapter getComponentAdapter() {
        return componentAdapter;
    }

    public interface OnItemListener {
        void onItemListener(int position);
    }
}
