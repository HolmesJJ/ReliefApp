package com.example.relief.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.adapter.detail.ChartsAdapter;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivityDetailBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.model.detail.Chart;
import com.example.relief.ui.viewmodel.DetailViewModel;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.DetailUtils;
import com.example.relief.utils.ListenerUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailViewModel> {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private ChartsAdapter detailChartsAdapter;
    private Handler mHandler;

    private List<Chart> detailCharts;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<DetailViewModel> getViewModelClazz() {
        return DetailViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String date = extras.getString("date");
            getBinding().tvDate.setText(date);
        }
        if (mHandler == null) {
            mHandler = new Handler();
        }
        String avatar = "https://cdn.discordapp.com/attachments/499918830999699470/928643427694940160/holmesjj.png";
        Glide.with(this).load(avatar).circleCrop().into(getBinding().ivAvatar);
        initBanner();
        initDetailSummary();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setOnClickListener();
    }

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
    }

    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/928708747625377812/download.jpeg");
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/928708748187406406/o627233.jpeg");
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/928708749630251038/1572679592-TGIevtgPbX.jpeg");

        getBinding().banner.setAdapter(new BannerImageAdapter<String>(images) {
            @Override
            public void onBindView(BannerImageHolder holder, String url, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView).load(url).into(holder.imageView);
            }
        });

        /**
         * @param leftItemWidth  item左展示的宽度,单位dp
         * @param rightItemWidth  item右展示的宽度,单位dp
         * @param pageMargin 页面间距,单位dp
         * @param scale      缩放[0-1],1代表不缩放
         */
        getBinding().banner.setBannerGalleryEffect(0, 50, 15, 1);
        getBinding().banner.start();
    }

    private void initDetailSummary() {
        detailCharts = DetailUtils.getDefaultMenus();
        if (detailCharts == null || detailCharts.size() == 0) {
            return;
        }
        detailChartsAdapter = new ChartsAdapter(ContextUtils.getContext(), detailCharts);
        getBinding().rvCharts.setAdapter(detailChartsAdapter);
        // GridLayoutManager，垂直方向
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ContextUtils.getContext(), 2, GridLayoutManager.VERTICAL, false);
        // 设置个数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (detailChartsAdapter.getItemViewType(position) == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        getBinding().rvCharts.setLayoutManager(gridLayoutManager);
        if (mHandler != null) {
            mHandler.post(loadDetailRunnable);
        }
    }

    private final Runnable loadDetailRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isChange = false;
            for (int i = 0; i < detailCharts.size(); i++) {
                Chart chart = detailCharts.get(i);
                if (chart.getCurrentProgress() < chart.getProgress()) {
                    chart.setCurrentProgress(chart.getCurrentProgress() + 1);
                    detailChartsAdapter.notifyItemChanged(i);
                    isChange = true;
                }
            }
            if (isChange) {
                mHandler.postDelayed(loadDetailRunnable, 10);
            }
        }
    };
}
