package com.sunshine.viewlibrary.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gengqiquan.library.utils.DensityUtils;
import com.sunshine.viewlibrary.refresh.interfaces.NoDataLayout;

/**
 * Created by 耿 on 2016/9/8.
 */
public class NoImgLableButtonNoDataLayout extends NoDataLayout {
    TextView mText;
    TextView mText2;
    Context mContext;

    public NoImgLableButtonNoDataLayout(Context context) {
        this(context, null);
    }

    public NoImgLableButtonNoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public NoImgLableButtonNoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context);
    }

    public void init(Context context) {
        mText = new TextView(context);
        mText.setGravity(Gravity.CENTER);
        mText.setTextSize(16);
        mText.setTextColor(0xff333333);
        mText2 = new TextView(context);
        mText2.setGravity(Gravity.CENTER);
        mText2.setTextSize(16);
        mText2.setTextColor(0xffffffff);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(DensityUtils.dp2px(context, 60), DensityUtils.dp2px(context, 20), DensityUtils.dp2px(context, 60), 0);
        linearLayout.addView(mText, textParams);
        LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 160), DensityUtils.dp2px(context, 40));
        text2Params.setMargins(0, DensityUtils.dp2px(context, 20), 0, 0);
        linearLayout.addView(mText2, text2Params);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        mText.setText("暂无数据");
    }

    @Override
    public NoDataLayout setLableText(String str) {
        mText.setText(str);
        return this;
    }

    @Override
    public NoDataLayout setLable2Text(String str) {
        if (str.length() > 6) {
            LinearLayout layout = (LinearLayout) getChildAt(0);
            layout.removeView(mText2);
            LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtils.dp2px(mContext, 40));
            text2Params.setMargins(0, DensityUtils.dp2px(mContext, 20), 0, 0);
            mText2.setPadding(DensityUtils.dp2px(mContext, 25), 0, DensityUtils.dp2px(mContext, 25), 0);
            layout.addView(mText2, text2Params);
        }
        mText2.setText(str);
        return this;
    }

    @Override
    public NoDataLayout setImageDrawable(int res) {
        return this;
    }

    public NoImgLableButtonNoDataLayout setButtonBackground(int res) {
        mText2.setBackgroundDrawable(getResources().getDrawable(res));
        return this;
    }

    public NoImgLableButtonNoDataLayout setButtonClickListener(OnClickListener listener) {
        mText2.setOnClickListener(listener);
        return this;
    }


}
