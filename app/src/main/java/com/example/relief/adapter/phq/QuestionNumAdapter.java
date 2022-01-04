package com.example.relief.adapter.phq;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.relief.R;
import com.example.relief.adapter.BaseAdapter;
import com.example.relief.adapter.ViewHolder;
import com.example.relief.model.Question;

import java.util.List;

public class QuestionNumAdapter extends BaseAdapter<Question> {

    private final OnItemListener onItemListener;

    public QuestionNumAdapter(Context context, List<Question> questions, OnItemListener onItemListener) {
        super(context, questions);
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindContentViews(RecyclerView.ViewHolder holder, int position) {
        Question question = getData().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.setText(R.id.tv_num, position + 1 + "");
        viewHolder.setVisible(R.id.v_selected, question.isSelected());

        viewHolder.setOnClickListener(R.id.ll_num, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemListener != null) {
                    onItemListener.onItemListener(position);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder initContentViews(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_question_num);
    }

    public interface OnItemListener {
        void onItemListener(int position);
    }
}
