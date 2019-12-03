package com.sunshine.viewlibrary.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
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
public class LableButtonNoDataLayout extends NoDataLayout {
    ImageView mImg;
    TextView mText;
    TextView mText2;

    public LableButtonNoDataLayout(Context context) {
        this(context, null);
    }

    public LableButtonNoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public LableButtonNoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    Context mContext;

    public TextView getmText() {
        return mText;
    }

    public TextView getmText2() {
        return mText2;
    }

    public void init() {
        mImg = new ImageView(mContext);
        mImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mText = new TextView(mContext);
        mText.setGravity(Gravity.CENTER);
        mText.setTextSize(16);
        mText.setTextColor(0xff333333);
        mText2 = new TextView(mContext);
        mText2.setGravity(Gravity.CENTER);
        mText2.setTextSize(16);
        mText2.setTextColor(0xffffffff);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(DensityUtils.dp2px(mContext, 125), DensityUtils.dp2px(mContext, 164));
        if (imgHeight > 0 && imgWidth > 0) {
            paramsImage = new LinearLayout.LayoutParams(imgWidth, imgHeight);
        }
        linearLayout.addView(mImg, paramsImage);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(DensityUtils.dp2px(mContext, 60), DensityUtils.dp2px(mContext, 20), DensityUtils.dp2px(mContext, 60), 0);
        linearLayout.addView(mText, textParams);
        LinearLayout.LayoutParams text2Params = new LinearLayout.LayoutParams(DensityUtils.dp2px(mContext, 160), DensityUtils.dp2px(mContext, 40));
        text2Params.setMargins(0, DensityUtils.dp2px(mContext, 20), 0, 0);
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

    /**
     * 超过6个字符，则更新按钮尺寸
     *
     * @param str
     * @return
     */
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
        mImg.setImageDrawable(getResources().getDrawable(res));
        return this;
    }

    public LableButtonNoDataLayout setButtonBackground(int res) {
        mText2.setBackgroundDrawable(getResources().getDrawable(res));
        return this;
    }

    public LableButtonNoDataLayout setButtonClickListener(OnClickListener listener) {
        mText2.setOnClickListener(listener);
        return this;
    }

    int imgWidth;
    int imgHeight;

    public LableButtonNoDataLayout setImageSize(int w, int h) throws Exception {
        if (0 == w || 0 == h) {
            throw new Exception("can't set width or height to 0");
        }
        imgWidth = w;
        imgHeight = h;

        removeAllViews();
        init();

        return this;
    }
}
