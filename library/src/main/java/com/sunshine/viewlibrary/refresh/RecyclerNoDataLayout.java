package com.sunshine.viewlibrary.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sunshine.viewlibrary.refresh.interfaces.NoDataLayout;

/**
 * Created by 耿 on 2016/9/8.
 */
@Deprecated
public class RecyclerNoDataLayout extends NoDataLayout {


    public RecyclerNoDataLayout(View view) {
        super(view.getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, layoutParams);
    }

    public RecyclerNoDataLayout(View view, LayoutParams layoutParams) {
        super(view.getContext());
        addView(view, layoutParams);
    }

    @Override
    public NoDataLayout setLableText(String str) {
        return this;
    }

    @Override
    public NoDataLayout setLable2Text(String str) {
        return this;
    }

    @Override
    public NoDataLayout setImageDrawable(int res) {
        return this;
    }


}
