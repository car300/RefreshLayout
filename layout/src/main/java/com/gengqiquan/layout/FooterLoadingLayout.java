package com.gengqiquan.layout;

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

import com.gengqiquan.layout.interfaces.FooterLayout;
import com.gengqiquan.layout.utils.DensityUtils;


/**
 * 这个类封装了下拉刷新的布局
 *
 * @author Li Hong
 * @since 2013-7-30
 */
public class FooterLoadingLayout extends RelativeLayout implements FooterLayout {
    /**
     * 进度条
     */
    private ProgressBar mProgressBar;
    /**
     * 显示的文本
     */
    private TextView mHintView;
    View lineLeft;
    View lineRight;

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
        lineLeft = new View(context);
        lineLeft.setBackgroundColor(0xffdddddd);
        lineRight = new View(context);
        lineRight.setBackgroundColor(0xffdddddd);
        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 40), DensityUtils.dp2px(context, 2));

        mProgressBar = new ProgressBar(context);
        mHintView = new TextView(context);
        mHintView.setTextColor(Color.BLACK);
        mHintView.setTextSize(14);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(DensityUtils.dp2px(context, 8), 0, DensityUtils.dp2px(context, 8), 0);
        linearLayout.addView(mProgressBar, new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 20), DensityUtils.dp2px(context, 30)));
        linearLayout.addView(lineLeft, lineParams);
        linearLayout.addView(mHintView, textParams);
        linearLayout.addView(lineRight, lineParams);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, params);
        onReset();
    }

    @Override
    public void onReset() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.GONE);
        lineLeft.setVisibility(View.GONE);
        lineRight.setVisibility(View.GONE);
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
    public void onNoMoreData() {
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.VISIBLE);
        lineLeft.setVisibility(View.VISIBLE);
        lineRight.setVisibility(View.VISIBLE);
        mHintView.setText("全部数据加载完毕");
    }
}

