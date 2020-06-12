package com.gengqiquan.library;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by gengqiquan on 2017/6/13.
 */
@Deprecated
public class SimpleRefreshLayout extends RefreshLayout {
    DefaultEmptyLayout mEmptyLayout;

    public SimpleRefreshLayout(Context context) {
        this(context, null);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEmptyLayout = new DefaultEmptyLayout(context);
        emptyView(mEmptyLayout);
    }

    public SimpleRefreshLayout emptyText(String str) {
        mEmptyLayout.emptyText(str);
        return this;
    }

    public SimpleRefreshLayout emptyImgRes(int res) {
        mEmptyLayout.emptyImgRes(res);
        return this;
    }
}
