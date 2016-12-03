package com.gengqiquan.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.gengqiquan.layout.interfaces.FooterLayout;
import com.gengqiquan.layout.interfaces.LoadMoreListener;
import com.gengqiquan.layout.interfaces.NoDataLayout;
import com.gengqiquan.layout.interfaces.RefreshListener;
import com.sunshine.adapterlibrary.interfaces.BAdapter;

/**
 * 以listview作为列表控件，
 * 集成了失败界面展示，
 * 点击重试按钮重试，
 * 下拉刷新，加载更多，
 * 无数据展示没有数据界面
 * Created by 耿 on 2016/9/7.
 */
public class SampleRefreshLayout extends RefreshLayout {
    String noDataLable = "暂无数据", noDataLable2 = "";
    int noDataImg = R.drawable.img_no_message;

    //设置无数据时界面
    public SampleRefreshLayout noDataView(NoDataLayout view) {
        removeView(mNoDataView);
        mNoDataView = view;
        mNoDataView.setLableText(noDataLable);
        mNoDataView.setLable2Text(noDataLable2);
        mNoDataView.setImageDrawable(noDataImg);
        addView(mNoDataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mNoDataView.setVisibility(View.GONE);
        return this;
    }

    //设置无数据时提示文本
    public SampleRefreshLayout noDataLable(String str) {
        noDataLable = str;
        mNoDataView.setLableText(str);
        return this;
    }

    //设置无数据时第二行提示文本
    public SampleRefreshLayout noDataLable2(String str) {
        noDataLable2 = str;
        mNoDataView.setLable2Text(str);
        return this;
    }

    //设置无数据时图片
    public SampleRefreshLayout noDataImg(int resId) {
        noDataImg = resId;
        mNoDataView.setImageDrawable(resId);
        return this;
    }

    //添加自定义加载更多界面
    public SampleRefreshLayout FooterLayout(FooterLayout layout) {
        mLoadMoreView = layout;
        return this;
    }


    @Override
    NoDataLayout onCreateNoDataView() {
        return new SimpleNoDataLayout(mContext);
    }

    @Override
    FooterLayout onCreateLoadMoreView() {
        return mLoadMoreView;
    }

    @Override
    View onCreateTopView() {
        return mTopView;
    }

    @Override
    View onCreateFailureView() {
        return mFailureView;
    }

    @Override
    public SampleRefreshLayout refreshEnable(boolean enable) {
        super.refreshEnable(enable);
        return this;
    }

    @Override
    public SampleRefreshLayout showTopView(boolean enable) {
        super.showTopView(enable);
        return this;
    }

    @Override
    public SampleRefreshLayout pageCount(int number) {
        super.pageCount(number);
        return this;
    }

    @Override
    public void doRefresh() {
        super.doRefresh();
    }


    @Override
    public SampleRefreshLayout loadMoreEnable(boolean b) {
        super.loadMoreEnable(b);
        return this;
    }


    @Override
    public SampleRefreshLayout refresh(RefreshListener refreshListener) {
        super.refresh(refreshListener);
        return this;
    }

    @Override
    public SampleRefreshLayout setOnListViewScrollListener(AbsListView.OnScrollListener scrollListener) {
        super.setOnListViewScrollListener(scrollListener);
        return this;
    }

    @Override
    public SampleRefreshLayout loadMore(LoadMoreListener loadMoreListener) {
        super.loadMore(loadMoreListener);
        return this;
    }

    @Override
    public SampleRefreshLayout adapter(BAdapter adapter) {
        super.adapter(adapter);
        return this;
    }

    public SampleRefreshLayout(Context context) {
        this(context, null);
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        build();
    }
}
