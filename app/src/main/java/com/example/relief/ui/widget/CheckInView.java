package com.example.relief.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.relief.R;

public class CheckInView extends FrameLayout {

    private Context mContext;
    private FrameLayout mFlCheckIn;
    private ImageView mIvIcon;

    public CheckInView(Context context) {

        this(context, null);
    }

    public CheckInView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CheckInView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.layout_check_in, this);

        mContext = context;
        mFlCheckIn = findViewById(R.id.fl_check_in);
        mIvIcon = findViewById(R.id.iv_icon);

        mIvIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.gray));

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckInView);
        mIvIcon.setImageDrawable(ta.getDrawable(R.styleable.CheckInView_icon));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size;
        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
            size = widthSize;
        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
            size = heightSize;
        } else {
            size = Math.min(widthSize, heightSize);
        }

        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }

    public void setCheckIn(boolean isCheckIn) {
        if (isCheckIn) {
            mIvIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.light_green));
            mFlCheckIn.setBackgroundColor(mContext.getColor(R.color.light_green));
        } else {
            mIvIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.gray));
            mFlCheckIn.setBackgroundColor(mContext.getColor(R.color.gray));
        }
    }
}
