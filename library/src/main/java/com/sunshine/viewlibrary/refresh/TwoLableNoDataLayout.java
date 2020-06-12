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
@Deprecated
public class TwoLableNoDataLayout extends NoDataLayout {
    ImageView mImg;
    TextView mText;
    TextView mText2;

    public TwoLableNoDataLayout(Context context) {
        this(context, null);
    }

    public TwoLableNoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public TwoLableNoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mImg = new ImageView(context);
        mImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mText = new TextView(context);
        mText.setGravity(Gravity.CENTER);
        mText.setTextSize(16);
        mText.setTextColor(0xff666666);
        mText2 = new TextView(context);
        mText2.setGravity(Gravity.CENTER);
        mText2.setTextSize(12);
        mText2.setTextColor(0xff999999);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(mImg, new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 125), DensityUtils.dp2px(context, 164)));
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, DensityUtils.dp2px(context, 20), 0, 0);
        linearLayout.addView(mText, textParams);
        LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        text2Params.setMargins(0, DensityUtils.dp2px(context, 10), 0, 0);
        linearLayout.addView(mText2, text2Params);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        mText.setText("暂无数据");
        mImg.setImageDrawable(getResources().getDrawable(R.drawable.message_default));
    }

    @Override
    public NoDataLayout setLableText(String str) {
        mText.setText(str);
        return this;
    }

    @Override
    public NoDataLayout setLable2Text(String str) {
        mText2.setText(str);
        return this;
    }

    @Override
    public NoDataLayout setImageDrawable(int res) {
        mImg.setImageDrawable(getResources().getDrawable(res));
        return this;
    }
}
