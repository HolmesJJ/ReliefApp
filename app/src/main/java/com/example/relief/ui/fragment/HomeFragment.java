package com.example.relief.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.relief.BR;
import com.example.relief.MainActivity;
import com.example.relief.R;
import com.example.relief.adapter.home.ChartsAdapter;
import com.example.relief.adapter.home.DateAdapter;
import com.example.relief.base.BaseFragment;
import com.example.relief.config.Config;
import com.example.relief.databinding.FragmentHomeBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.model.home.Chart;
import com.example.relief.model.home.DateOfMonth;
import com.example.relief.ui.viewmodel.HomeViewModel;
import com.example.relief.utils.ContextUtils;
import com.example.relief.utils.DateUtils;
import com.example.relief.utils.ListenerUtils;
import com.example.relief.utils.SummaryUtils;
import com.example.relief.utils.ToastUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private long currentTime = System.currentTimeMillis();
    private DateAdapter dateAdapter;
    private ChartsAdapter weeklyChartsAdapter;
    private ChartsAdapter monthlyChartsAdapter;
    private Handler mHandler;

    private List<Chart> weeklyCharts;
    private List<Chart> monthlyCharts;

    private int selectedDate;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<HomeViewModel> getViewModelClazz() {
        return HomeViewModel.class;
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        super.initData();
        if (mHandler == null) {
            mHandler = new Handler();
        }
        String avatar = "https://cdn.discordapp.com/attachments/499918830999699470/928643427694940160/holmesjj.png";
        Glide.with(this).load(avatar).circleCrop().into(getBinding().ivAvatar);
        if (Config.isPhqDone() && Config.isEmotionDone() && Config.isSentimentDone() && Config.isMonitorDone()) {
            getBinding().llNotCheckIn.setVisibility(View.GONE);
            getBinding().llCheckIn.setVisibility(View.VISIBLE);
        } else {
            getBinding().llCheckIn.setVisibility(View.GONE);
            getBinding().llNotCheckIn.setVisibility(View.VISIBLE);
        }
        initBanner();
        isRightVisited();
        initCalendar();
        initWeeklySummary();
        initMonthlySummary();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
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

    private void setObserveListener() {
        getViewModel().getActivityAction().observe(this, activityAction -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).stopLoading();
            }
            if (activityAction != null) {
                try {
                    Intent intent = new Intent(getActivity(), activityAction);
                    String date = DateUtils.formatDate(selectedDate) + " " + getBinding().tvCurrentMonth.getText();
                    intent.putExtra("date", date);
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

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().ivLeft, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (dateAdapter == null) {
                    return;
                }
                currentTime = DateUtils.lastMonth(currentTime);
                String monthYear = DateUtils.getMonth(currentTime) + " " + DateUtils.getYear(currentTime);
                getBinding().tvCurrentMonth.setText(monthYear);
                List<DateOfMonth> datesOfMonth = DateUtils.getDatesOfMonth(currentTime);
                dateAdapter.setData(datesOfMonth);
                dateAdapter.notifyDataSetChanged();
                isRightVisited();
            }
        });
        ListenerUtils.setOnClickListener(getBinding().ivRight, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                currentTime = DateUtils.nextMonth(currentTime);
                String monthYear = DateUtils.getMonth(currentTime) + " " + DateUtils.getYear(currentTime);
                getBinding().tvCurrentMonth.setText(monthYear);
                List<DateOfMonth> datesOfMonth = DateUtils.getDatesOfMonth(currentTime);
                dateAdapter.setData(datesOfMonth);
                dateAdapter.notifyDataSetChanged();
                isRightVisited();
            }
        });
    }

    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/927768501505101844/ace804c04954d534ceec36b99870c525.jpeg");
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/928650662781255770/Signs-orig.jpeg");
        images.add("https://cdn.discordapp.com/attachments/499918830999699470/927768500922114118/Be-Happy-460.jpeg");

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

    private void initCalendar() {
        String monthYear = DateUtils.getMonth(currentTime) + " " + DateUtils.getYear(currentTime);
        getBinding().tvCurrentMonth.setText(monthYear);
        List<DateOfMonth> datesOfMonth = DateUtils.getDatesOfMonth(currentTime);
        dateAdapter = new DateAdapter(ContextUtils.getContext(), datesOfMonth, new DateAdapter.OnItemListener() {
            @Override
            public void onItemListener(int position) {
                selectedDate = position + 1;
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoading(false);
                }
                getViewModel().toDetail();
            }
        });
        getBinding().rvDates.setAdapter(dateAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        getBinding().rvDates.setLayoutManager(layoutManager);
    }

    private void isRightVisited() {
        if (DateUtils.isSameYear(currentTime) && DateUtils.isSameMonth(currentTime)) {
            getBinding().ivRight.setClickable(false);
            getBinding().ivRight.setVisibility(View.INVISIBLE);
        } else {
            getBinding().ivRight.setVisibility(View.VISIBLE);
            getBinding().ivRight.setClickable(true);
        }
    }

    private void initWeeklySummary() {
        weeklyCharts = SummaryUtils.getDefaultMenus("weekly_summary.json");
        if (weeklyCharts == null || weeklyCharts.size() == 0) {
            return;
        }
        weeklyChartsAdapter = new ChartsAdapter(ContextUtils.getContext(), weeklyCharts);
        getBinding().rvWeeklyHarts.setAdapter(weeklyChartsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        getBinding().rvWeeklyHarts.setLayoutManager(layoutManager);
        if (mHandler != null) {
            mHandler.post(loadWeeklySummaryRunnable);
        }
    }

    private void initMonthlySummary() {
        monthlyCharts = SummaryUtils.getDefaultMenus("monthly_summary.json");
        if (monthlyCharts == null || monthlyCharts.size() == 0) {
            return;
        }
        monthlyChartsAdapter = new ChartsAdapter(ContextUtils.getContext(), monthlyCharts);
        getBinding().rvMonthlyCharts.setAdapter(monthlyChartsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ContextUtils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        getBinding().rvMonthlyCharts.setLayoutManager(layoutManager);
        if (mHandler != null) {
            mHandler.post(loadMonthlySummaryRunnable);
        }
    }

    private final Runnable loadWeeklySummaryRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isChange = false;
            for (int i = 0; i < weeklyCharts.size(); i++) {
                Chart chart = weeklyCharts.get(i);
                if (chart.getType() == 2 && chart.getCurrentProgress() < chart.getProgress()) {
                    chart.setCurrentProgress(chart.getCurrentProgress() + 1);
                    weeklyChartsAdapter.notifyItemChanged(i);
                    isChange = true;
                }
            }
            if (isChange) {
                mHandler.postDelayed(loadWeeklySummaryRunnable, 10);
            }
        }
    };

    private final Runnable loadMonthlySummaryRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isChange = false;
            for (int i = 0; i < monthlyCharts.size(); i++) {
                Chart chart = monthlyCharts.get(i);
                if (chart.getType() == 2 && chart.getCurrentProgress() < chart.getProgress()) {
                    chart.setCurrentProgress(chart.getCurrentProgress() + 1);
                    monthlyChartsAdapter.notifyItemChanged(i);
                    isChange = true;
                }
            }
            if (isChange) {
                mHandler.postDelayed(loadMonthlySummaryRunnable, 10);
            }
        }
    };
}
