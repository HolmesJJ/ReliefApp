package com.example.relief.adapter.detail;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import com.example.relief.R;
import com.example.relief.adapter.BaseAdapter;
import com.example.relief.adapter.ViewHolder;
import com.example.relief.model.detail.Chart;
import com.example.relief.ui.widget.DonutChartView;

import java.util.List;

public class ChartsAdapter extends BaseAdapter<Chart> {

    public ChartsAdapter(Context context, List<Chart> charts) {
        super(context, charts);
    }

    @Override
    public void onBindContentViews(RecyclerView.ViewHolder holder, int position) {
        Chart chart = getData().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setText(R.id.tv_title, chart.getTitle());

        // 圆形type
        if (getData().get(position).getType() == 2) {
            DonutChartView donutChartView = viewHolder.getView(R.id.tasks_view);
            donutChartView.setProgress(chart.getCurrentProgress());
        } else {
            ProgressBar progress = viewHolder.getView(R.id.pb_progress);
            progress.setProgress(chart.getCurrentProgress());
        }
    }

    @Override
    public RecyclerView.ViewHolder initContentViews(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_detail_bar_chart);
        }
        return ViewHolder.createViewHolder(getContext(), parent, R.layout.item_detail_donut_chart);
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }

    public interface OnItemListener {
        void onItemListener(int position);
    }
}
