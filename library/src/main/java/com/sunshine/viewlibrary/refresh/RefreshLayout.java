package com.sunshine.viewlibrary.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.NestedScrollingChild;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gengqiquan.library.R;
import com.gengqiquan.library.utils.DensityUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.sunshine.adapterlibrary.interfaces.BAdapter;
import com.sunshine.viewlibrary.refresh.interfaces.FooterLayout;
import com.sunshine.viewlibrary.refresh.interfaces.LoadMoreListener;
import com.sunshine.viewlibrary.refresh.interfaces.NoDataLayout;
import com.sunshine.viewlibrary.refresh.interfaces.RefreshListener;

import java.util.List;

/**
 * 以listview作为列表控件，
 * 集成了失败界面展示，
 * 点击重试按钮重试，
 * 下拉刷新，加载更多，
 * 无数据展示没有数据界面
 * Created by 耿 on 2016/9/7.
 */
@Deprecated
@CoordinatorLayout.DefaultBehavior(AppBarLayout.ScrollingViewBehavior.class)
public class RefreshLayout extends RelativeLayout implements NestedScrollingChild {
    public final static int LISTMODE_RECYCLER = 0;
    public final static int LISTMODE_LISTVIEW_OR_GRIDVIEW = 1;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mListView;
    Context mContext;
    BAdapter adapter;
    RefreshListener mRefreshListener;
    LoadMoreListener mLoadMoreListener;
    AbsListView.OnScrollListener mScrollListener;
    View mFailureView;
    View mTopView;
    NoDataLayout mNoDataView;
    boolean isLoadMore = false;
    boolean hasMoreData = false;
    boolean loadMoreEnable = true;
    boolean hideFooter = false;
    boolean showTopView = false;
    FooterLayout mLoadMoreView;
    public int LISTMODE = LISTMODE_LISTVIEW_OR_GRIDVIEW;
    String noDataLable = "", noDataLable2 = "";
    int noDataImg = R.drawable.img_no_message;
    int pageCount = 20;

    @SuppressLint("InflateParams")
    private void init() {
        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        addView(mSwipeRefreshLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView = new ListView(mContext);
        mListView.setDivider(new BitmapDrawable());
        mListView.setSelector(new BitmapDrawable());
        mListView.setDividerHeight(0);
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSwipeRefreshLayout.addView(mListView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mSwipeRefreshLayout.setColorSchemeColors(0xffff2500);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         isLoadMore = false;
                                                         if (mRefreshListener != null) {
                                                             mRefreshListener.onRefresh();
                                                         }
                                                     }
                                                 }
        );
        mFailureView = LayoutInflater.from(mContext).inflate(R.layout.layout_bad_network1, null);
        mFailureView.findViewById(R.id.reload).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFailureView.setVisibility(View.GONE);
                doRefresh();
            }
        });
        mNoDataView = new SimpleNoDataLayout(mContext);
        addView(mNoDataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mNoDataView.setVisibility(View.GONE);
        addView(mFailureView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mLoadMoreView = new FooterLoadingLayout(mContext);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreView.setAddLayoutParams(params);
        mListView.addFooterView(mLoadMoreView.getFooterView(), null, false);

        mTopView = new View(mContext);
        mTopView.setBackgroundResource(R.drawable.ic_menu_top);

        LayoutParams topLayoutParams = new LayoutParams(DensityUtils.dp2px(mContext, 42), DensityUtils.dp2px(mContext, 42));
        topLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        topLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        topLayoutParams.setMargins(0, 0, DensityUtils.dp2px(mContext, 16), DensityUtils.dp2px(mContext, 20));
        addView(mTopView, topLayoutParams);
        mTopView.setVisibility(View.GONE);
        mTopView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setSelection(0);
                mTopView.setVisibility(View.GONE);
                if (topListener != null) {
                    topListener.onClick(v);
                }
            }
        });
    }

    private OnClickListener topListener;

    public RefreshLayout setTopListener(OnClickListener topListener) {
        this.topListener = topListener;
        return this;
    }

    public RefreshLayout refreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
        return this;
    }

    public RefreshLayout showTopView(boolean enable) {
        showTopView = enable;
        return this;
    }

    public RefreshLayout ListMode(int mode) {
        this.LISTMODE = mode;
        return this;
    }

    /*
     *分页数量
     *@author Administrator
     *@date 2016/10/22 16:35
     */
    public RefreshLayout pageCount(int number) {
        this.pageCount = number;
        return this;
    }

    /*
     *手动调用刷新
     *@author 耿
     *@date 2016/9/8 15:06
     */
    public void doRefresh() {
        showRefresh();
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    /**
     * 手动调用加载更多
     */
    public void loadMore() {
        if (hasMoreData && mLoadMoreListener != null) {
            mLoadMoreListener.LoadMore();
            mLoadMoreView.onRefreshing();
            isLoadMore = true;
        }
    }

    /**
     * 只显示刷新效果
     *
     * @author gengqiquan
     * @date 2017/8/1 下午3:55
     */
    public void showRefresh() {
//        mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setRefreshing(true);
        mNoDataView.setVisibility(View.GONE);
    }

    //刷新完成添加列表数据
    public void refreshComplete(List list) {
        adapter.appendList(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mFailureView.findViewById(R.id.bad_network).setVisibility(View.GONE);
        if (list.size() == 0) {
            if (mNoDataView != null) {
                mNoDataView.setVisibility(View.VISIBLE);
            }
            mTopView.setVisibility(View.GONE);
            mFailureView.findViewById(R.id.bad_network).setVisibility(View.GONE);
        } else {
            mNoDataView.setVisibility(View.GONE);
        }
        if (list.size() >= pageCount) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
    }

    public void refreshFinish() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    //设置无数据时界面
    public RefreshLayout noDataView(NoDataLayout view) {
        removeView(mNoDataView);
        mNoDataView = view;
        mNoDataView.setLableText(noDataLable);
        mNoDataView.setLable2Text(noDataLable2);
        mNoDataView.setImageDrawable(noDataImg);
        addView(mNoDataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mNoDataView.setVisibility(View.GONE);
        return this;
    }

    //设置无数据时提示文本
    public RefreshLayout noDataLable(String str) {
        noDataLable = str;
        mNoDataView.setLableText(str);
        return this;
    }

    //设置无数据时第二行提示文本
    public RefreshLayout noDataLable2(String str) {
        noDataLable2 = str;
        mNoDataView.setLable2Text(str);
        return this;
    }

    //设置无数据时图片
    public RefreshLayout noDataImg(int resId) {
        noDataImg = resId;
        mNoDataView.setImageDrawable(resId);
        return this;
    }

    //加载更多完成后添加数据
    public void loadMoreComplete(List list) {
        adapter.addList(list);
        if (list.size() >= pageCount) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
        isLoadMore = false;
    }

    //数据请求失败调用
    public void Failure() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (!isLoadMore) {
            adapter.getList().clear();
            adapter.notifyDataChanged();
            mFailureView.findViewById(R.id.bad_network).setVisibility(View.VISIBLE);
            if (mNoDataView != null) {
                mNoDataView.setVisibility(GONE);
            }
        } else {
            hasMoreData = false;
            isLoadMore = false;
            mLoadMoreView.onRefreshFailure();
        }

    }

    //添加自定义没有数据时的界面
    public RefreshLayout addNoDataView(NoDataLayout v) {
        mNoDataView = v;
        return this;
    }

    //添加自定义加载更多界面
    public RefreshLayout FooterLayout(FooterLayout layout) {
        mLoadMoreView = layout;
        return this;
    }

    //是否允许控件加载更多
    public RefreshLayout loadMoreEnable(boolean b) {
        loadMoreEnable = b;
        return this;
    }

    //没有更多数据是否隐藏Footer
    public RefreshLayout hideFooter() {
        hideFooter = true;
        return this;
    }

    //行距
    public RefreshLayout dividerHeight(int height) {
        mListView.setDividerHeight(height);
        return this;
    }

    //分割线
    public RefreshLayout divider(@DrawableRes int resID) {
        mListView.setDivider(getResources().getDrawable(resID));
        return this;
    }

    public BAdapter getAdapter() {
        return this.adapter;
    }

    public List getList() {
        return this.adapter.getList();
    }

    public ListView getListView() {
        return mListView;
    }

    //某次请求后。是否有更多数据需要加载
    public void hasMoreData(boolean b) {
        if (!loadMoreEnable)
            return;
        hasMoreData = b;
        isLoadMore = false;
        if (hasMoreData) {
            mLoadMoreView.onReset();
        } else {//没有更多数据
            if (getList().size() == 0 || hideFooter) {//列表为空不显示footer||设置隐藏不显示footer
                mLoadMoreView.hide();
            } else {
                mLoadMoreView.onNoMoreData();
            }
        }
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int newState) {
                if (loadMoreEnable && hasMoreData && !isLoadMore) {
                    if (newState == ListView.OnScrollListener.SCROLL_STATE_IDLE
                            || newState == ListView.OnScrollListener.SCROLL_STATE_FLING) {
                        if (isLastItemVisible()) {
                            if (mLoadMoreListener != null) {
                                mLoadMoreListener.LoadMore();
                                mLoadMoreView.onRefreshing();
                                isLoadMore = true;
                            }
                        }
                    }
                }
                if (showTopView) {
                    switch (newState) {
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                            mTopView.setVisibility(View.GONE);
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                            if (mListView.getFirstVisiblePosition() > 0)
                                mTopView.setVisibility(View.VISIBLE);
                            else
                                mTopView.setVisibility(View.GONE);
                            break;
                    }
                }
                if (null != mScrollListener) {
                    mScrollListener.onScrollStateChanged(absListView, newState);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (null != mScrollListener) {
                    mScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }

        });

    }


    private boolean isLastItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = mListView.getLastVisiblePosition();

        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - mListView.getFirstVisiblePosition();
            final int childCount = mListView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mListView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mListView.getBottom();
            }
        }

        return false;
    }

    //下拉刷新回调
    public RefreshLayout Refresh(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        return this;
    }

    //添加listview滚动监听
    public RefreshLayout setOnListViewScrollListener(AbsListView.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
        return this;
    }

    //加载更多回调
    public RefreshLayout LoadMore(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
        return this;
    }

    //设置适配器
    public RefreshLayout setAdapter(BAdapter adapter) {
        this.adapter = adapter;
        mListView.setAdapter((BaseAdapter) adapter.getAdapter());
        return this;
    }

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
}
