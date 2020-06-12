package com.sunshine.viewlibrary.refresh;

/**
 * Created by 耿 on 2016/9/7.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gengqiquan.library.utils.DensityUtils;
import com.sunshine.viewlibrary.refresh.interfaces.FooterLayout;


/**
 * 这个类封装了下拉刷新的布局
 *
 * @author Li Hong
 * @since 2013-7-30
 */
@Deprecated
public class FooterLoadingLayout extends RelativeLayout implements FooterLayout {
    /**
     * 进度条
     */
    private ProgressBar mProgressBar;
    /**
     * 显示的文本
     */
    private TextView mHintView;

    /**
     * 构造方法
     *
     * @param context context
     */
    public FooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public FooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        mProgressBar = new ProgressBar(context);
        mHintView = new TextView(context);
        mHintView.setTextColor(Color.BLACK);
        mHintView.setTextSize(14);
        mHintView.setIncludeFontPadding(false);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 13), DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 13));
        linearLayout.addView(mProgressBar, new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 20), DensityUtils.dp2px(context, 30)));
        linearLayout.addView(mHintView, textParams);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        hide();
    }

    @Override
    public void onReset() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText("上拉加载更多");
    }

    @Override
    public void onRefreshFailure() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText("加载更多失败");
    }

    @Override
    public View getFooterView() {
        return this;
    }

    @Override
    public View setAddLayoutParams(ViewGroup.LayoutParams params) {
        setLayoutParams(params);
        return this;
    }


    @Override
    public void onRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText("正在加载更多");
    }

    @Override
    public void hide() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.GONE);
    }

    @Override
    public void onNoMoreData() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText("全部数据加载完毕");
    }
}

