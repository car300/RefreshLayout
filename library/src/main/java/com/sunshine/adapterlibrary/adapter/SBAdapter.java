package com.sunshine.adapterlibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sunshine.adapterlibrary.interfaces.BAdapter;
import com.sunshine.adapterlibrary.interfaces.Converter;
import com.sunshine.adapterlibrary.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的ListView/GridView的Adapter
 *
 * @author gengqiqauan
 * @time 2015/4/22
 */
public class SBAdapter<T> extends BaseAdapter implements BAdapter<BaseAdapter> {

    private Context mContext;
    private List<? super T> list;
    private int mItemLayoutId;
    private Converter<? super T> converter;

    public SBAdapter(Context context) {
        this.mContext = context;
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = new ArrayList<>();
    }

    public SBAdapter(Context context, List list) {
        this.mContext = context;
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = list;
    }

    public SBAdapter(Context context, List list, int itemLayoutId) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.list = list;
    }

    public SBAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.list = new ArrayList();
    }

    public SBAdapter<T> list(List list) {
        this.list = list;
        return this;
    }

    public SBAdapter<T> layout(int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        return this;
    }

    public SBAdapter<T> bindViewData(Converter<? super T> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public List getList() {
        return this.list;
    }

    @Override
    public void appendList(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public BaseAdapter getAdapter() {
        return this;
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void addList(List list2) {
        this.list.addAll(list2);
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return (T) this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        converter.convert(viewHolder, getItem(position));
        return viewHolder.getItemView();

    }


    public ViewHolder getViewHolder(int position, View convertView,
                                    ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public void notifyDataSetChanged(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }

    }
}
