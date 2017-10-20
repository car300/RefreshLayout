package com.gengqiquan.library;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by gengqiquan on 2017/6/13.
 */

public class SimpleRefreshLayout extends RefreshLayout {
    SimpleNoDataLayout mSimpleNoDataLayout;

    public SimpleRefreshLayout(Context context) {
        this(context, null);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleNoDataLayout = new SimpleNoDataLayout(context);
    }

    public SimpleRefreshLayout setNoDataLableText(String str) {
        mSimpleNoDataLayout.noDataLableText(str);
        return this;
    }

    public SimpleRefreshLayout setNoDataImgRes(int res) {
        mSimpleNoDataLayout.noDataImgRes(res);
        return this;
    }
}
