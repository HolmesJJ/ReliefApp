package com.example.relief.adapter.phq;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.relief.R;
import com.example.relief.adapter.BaseAdapter;
import com.example.relief.adapter.ViewHolder;
import com.example.relief.model.Component;

import java.util.List;

public class ComponentAdapter extends BaseAdapter<Component> {

    private final OnItemListener onItemListener;

    public ComponentAdapter(Context context, List<Component> components, OnItemListener onItemListener) {
        super(context, components);
        this.onItemListener = onItemListener;
    }

    @Override
    public void onBindContentViews(RecyclerView.ViewHolder holder, int position) {
        Component component = getData().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.setText(R.id.tvContent, component.getContent());
        if (component.isSelected()) {
            viewHolder.setBackgroundRes(R.id.tvContent, R.drawable.bg_component_select_round);
            viewHolder.setTextColorRes(R.id.tvContent, R.color.white);
        } else {
            viewHolder.setBackgroundRes(R.id.tvContent, R.drawable.bg_component_unselect_round);
            viewHolder.setTextColorRes(R.id.tvContent, R.color.black);
        }

        viewHolder.setOnClickListener(R.id.tvContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < getData().size(); i++) {
                    Component component = getData().get(i);
                    component.setSelected(false);
                    notifyItemChanged(i);
                }
                component.setSelected(true);
                notifyItemChanged(position);
                if (onItemListener != null) {
                    onItemListener.onItemListener();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder initContentViews(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_component);
    }

    public interface OnItemListener {
        void onItemListener();
    }
}
