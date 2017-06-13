package com.gengqiquan.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gengqiquan.library.utils.DensityUtils;


/**
 * Created by 耿 on 2016/9/8.
 */
public class SimpleNoDataLayout extends RefreshLayout {
    ImageView mImg;
    TextView mText;

    public SimpleNoDataLayout(Context context) {
        this(context, null);
    }

    public SimpleNoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SimpleNoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mImg = new ImageView(context);
        mText = new TextView(context);
        mText.setGravity(Gravity.CENTER);
        mText.setTextSize(16);
        mText.setTextColor(0xff666666);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(mImg, new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, DensityUtils.dp2px(context, 15), 0, 0);
        linearLayout.addView(mText, textParams);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        mText.setText("暂无数据");
        mImg.setImageDrawable(getResources().getDrawable(R.drawable.img_no_message));
    }

    public SimpleNoDataLayout noDataLableText(String str) {
        mText.setText(str);
        return this;
    }

    public SimpleNoDataLayout noDataImgRes(int res) {
        mImg.setImageDrawable(getResources().getDrawable(res));
        return this;
    }


}
