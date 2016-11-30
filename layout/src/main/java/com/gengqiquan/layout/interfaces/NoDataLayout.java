package com.gengqiquan.layout.interfaces;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by 耿 on 2016/9/8.
 */
public abstract class NoDataLayout extends RelativeLayout {


    public NoDataLayout(Context context) {
        this(context, null);
    }

    public NoDataLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public NoDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public abstract NoDataLayout setLableText(String str);
    public abstract NoDataLayout setLable2Text(String str);
    public abstract NoDataLayout setImageDrawable(int res);

}
