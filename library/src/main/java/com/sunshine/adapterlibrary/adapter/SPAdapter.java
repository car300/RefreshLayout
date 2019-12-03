package com.sunshine.adapterlibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sunshine.adapterlibrary.interfaces.BAdapter;
import com.sunshine.adapterlibrary.interfaces.PConverter;
import com.sunshine.adapterlibrary.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的ListView/GridView的Adapter
 *
 * @author gengqiqauan
 * @time 2015/4/22
 */
public class SPAdapter<T> extends BaseAdapter implements BAdapter<BaseAdapter> {
    private Context mContext;
    private List<? super T> list;
    private int mItemLayoutId;
    PConverter<? super T> pConverter;

    public SPAdapter(Context context) {
        this.mContext = context;
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = new ArrayList<>();
    }

    public SPAdapter(Context context, List list) {
        this.mContext = context;
        this.mItemLayoutId = new LinearLayout(mContext).getId();
        this.list = list;

    }

    public SPAdapter(Context context, List list, int itemLayoutId) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.list = list;

    }

    public SPAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.mItemLayoutId = itemLayoutId;
        this.list = new ArrayList();

    }

    public SPAdapter<T> list(List list) {
        this.list = list;
        return this;
    }

    public SPAdapter<T> layout(int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        return this;
    }


    public SPAdapter<T> bindPositionData(PConverter<? super T> pConverter) {
        this.pConverter = pConverter;
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
        pConverter.convert(viewHolder, getItem(position), position);
        return viewHolder.getItemView();

    }


    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    /**
     * 局部刷新
     *
     * @param listView
     * @param position
     */
    public void updateSingleRow(ListView listView, int position) {
        if (listView != null) {
            //获取第一个显示的item
            int visiblePos = listView.getFirstVisiblePosition();
            //计算出当前选中的position和第一个的差，也就是当前在屏幕中的item位置
            int offset = position - visiblePos;
            int lenth = listView.getChildCount();
            // 只有在可见区域才更新,因为不在可见区域得不到Tag,会出现空指针,所以这是必须有的一个步骤
            if ((offset < 0) || (offset >= lenth)) return;
            View convertView = listView.getChildAt(offset);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            // 假删除
            viewHolder.getItemView().setVisibility(View.GONE);
        }
    }

}
