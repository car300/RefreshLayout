package com.sunshine.adapterlibrary.interfaces;

import android.view.View;

/**
 * Created by 耿 on 2016/9/23.
 */
public interface RVBAdapter<T> extends com.sunshine.adapterlibrary.interfaces.BAdapter<T> {

    public RVBAdapter<T> addHeaderView(View headerView);

    public RVBAdapter<T> addFooterView(View footerView);

}
