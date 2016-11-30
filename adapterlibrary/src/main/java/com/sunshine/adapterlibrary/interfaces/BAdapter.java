package com.sunshine.adapterlibrary.interfaces;

import android.widget.Adapter;

import java.util.List;

/**
 * Created by 耿 on 2016/9/23.
 */
public interface BAdapter<T> {
    public List getList();

    public void appendList(List list);
    public T getAdapter();
    //刷新数据
    public void notifyDataChanged();

    public void addList(List list);

}
