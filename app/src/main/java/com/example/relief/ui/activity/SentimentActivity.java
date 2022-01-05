package com.example.relief.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.relief.BR;
import com.example.relief.R;
import com.example.relief.api.model.sentiment.SentimentEnum;
import com.example.relief.base.BaseActivity;
import com.example.relief.databinding.ActivitySentimentBinding;
import com.example.relief.listener.OnMultiClickListener;
import com.example.relief.ui.viewmodel.SentimentViewModel;
import com.example.relief.utils.ListenerUtils;

public class SentimentActivity extends BaseActivity<ActivitySentimentBinding, SentimentViewModel> implements ActivityResultCallback<Uri> {

    private static final String TAG = SentimentActivity.class.getSimpleName();

    private final ActivityResultLauncher<String> openGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(), this);

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sentiment;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public Class<SentimentViewModel> getViewModelClazz() {
        return SentimentViewModel.class;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        setObserveListener();
        setOnClickListener();
        doIsShowLoading();
    }

    @Override
    public void onActivityResult(Uri result) {
        if (result != null) {
            showLoading(true);
            getViewModel().getImageUri().setValue(result);
            getBinding().llAnalysisUpload.setVisibility(View.GONE);
            getBinding().svAnalysisResult.setVisibility(View.VISIBLE);
            getBinding().ivPost.setImageURI(result);
            getViewModel().upload();
        }
    }

    private void setObserveListener() {
        getViewModel().getContent().observe(this, content -> {
            getBinding().tvContent.setText(content);
        });
        getViewModel().getSentiment().observe(this, sentiment -> {
            if (SentimentEnum.POSITIVE.getValue().equals(sentiment)) {
                getBinding().tvSentiment.setTextColor(getResources().getColor(R.color.light_green, this.getTheme()));
            } else if (SentimentEnum.NEUTRAL.getValue().equals(sentiment)) {
                getBinding().tvSentiment.setTextColor(getResources().getColor(R.color.golden_yellow, this.getTheme()));
            } else if (SentimentEnum.NEGATIVE.getValue().equals(sentiment)) {
                getBinding().tvSentiment.setTextColor(getResources().getColor(R.color.red, this.getTheme()));
            } else {
                getBinding().tvSentiment.setTextColor(getResources().getColor(R.color.black, this.getTheme()));
            }
            getBinding().tvSentiment.setText(sentiment);
        });
        getViewModel().getTarget().observe(this, sentiment -> {
            getBinding().tvTarget.setText(sentiment);
        });
        getViewModel().getKeyword().observe(this, keyword -> {
            getBinding().tvKeyword.setText(keyword);
        });
        getViewModel().getSuggestion().observe(this, suggestion -> {
            getBinding().tvSuggestion.setText(suggestion);
        });
    }

    private void setOnClickListener() {
        ListenerUtils.setOnClickListener(getBinding().llAnalysisUpload, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg, image/png"等的类型 所有类型则写"image/*"
                openGallery.launch("image/*");
            }
        });
        ListenerUtils.setOnClickListener(getBinding().ivBack, new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
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
