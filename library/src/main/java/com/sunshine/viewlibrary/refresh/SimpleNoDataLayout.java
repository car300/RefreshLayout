package com.sunshine.viewlibrary.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gengqiquan.library.R;
import com.gengqiquan.library.utils.DensityUtils;
import com.sunshine.viewlibrary.refresh.interfaces.NoDataLayout;

/**
 * Created by 耿 on 2016/9/8.
 */
public class SimpleNoDataLayout extends NoDataLayout {
    ImageView mImg;
    TextView mText;
    Context context;

    public SimpleNoDataLayout(Context context) {
        this(context, null);
    }

    public SimpleNoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SimpleNoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        mImg = new ImageView(context);
        mImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mText = new TextView(context);
        mText.setGravity(Gravity.CENTER);
        mText.setTextSize(16);
        mText.setTextColor(0xff333333);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 125), DensityUtils.dp2px(context, 164));
        if (imgHeight > 0 && imgWidth > 0) {
            paramsImage = new LinearLayout.LayoutParams(imgWidth, imgHeight);
        }
        linearLayout.addView(mImg, paramsImage);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, DensityUtils.dp2px(context, 20), 0, 0);
        linearLayout.addView(mText, textParams);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        mText.setText("暂无数据");
        mImg.setImageDrawable(getResources().getDrawable(R.drawable.img_no_message));
    }

    @Override
    public NoDataLayout setLableText(String str) {
        mText.setText(str);
        return this;
    }

    @Override
    public NoDataLayout setLable2Text(String str) {
        return this;
    }

    @Override
    public NoDataLayout setImageDrawable(int res) {
        mImg.setImageDrawable(getResources().getDrawable(res));
        return this;
    }

    public ImageView getImg() {
        return mImg;
    }

    int imgWidth;
    int imgHeight;

    public SimpleNoDataLayout setImageSize(int w, int h) {
        imgWidth = w;
        imgHeight = h;
        removeAllViews();
        init();
        return this;
    }
}
