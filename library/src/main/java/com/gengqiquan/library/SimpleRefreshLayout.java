package com.gengqiquan.library;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by gengqiquan on 2017/6/13.
 */

public class SimpleRefreshLayout extends RefreshLayout {
    SimpleRefreshLayout mSimpleRefreshLayout;

    public SimpleRefreshLayout(Context context) {
        this(context, null);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleRefreshLayout = new SimpleRefreshLayout(context);
    }

    public SimpleRefreshLayout setNoDataLableText(String str) {
        mSimpleRefreshLayout.setNoDataLableText(str);
        return this;
    }

    public SimpleRefreshLayout setNoDataImgRes(int res) {
        mSimpleRefreshLayout.setNoDataImgRes(res);
        return this;
    }
}
