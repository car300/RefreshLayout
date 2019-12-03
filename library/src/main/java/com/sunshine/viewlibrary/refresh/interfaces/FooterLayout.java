package com.sunshine.viewlibrary.refresh.interfaces;

import android.view.View;
import android.view.ViewGroup;

/**
 * 加载更多接口
 * Created by 耿 on 2016/9/8.
 */
public interface FooterLayout {
    public void onReset();

    public void onRefreshFailure();

    public View getFooterView();

    public View setAddLayoutParams(ViewGroup.LayoutParams params);

    public void onRefreshing();

    public void onNoMoreData();

    public void hide();
}
