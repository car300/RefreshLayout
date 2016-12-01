package com.gengqiquan.layout;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gengqiquan.layout.interfaces.FooterLayout;
import com.gengqiquan.layout.interfaces.LoadMoreListener;
import com.gengqiquan.layout.interfaces.NoDataLayout;
import com.gengqiquan.layout.interfaces.RefreshListener;
import com.gengqiquan.layout.utils.DensityUtils;
import com.sunshine.adapterlibrary.interfaces.BAdapter;

import java.util.List;

/**
 * 以listview作为列表控件，
 * 集成了失败界面展示，
 * 点击重试按钮重试，
 * 下拉刷新，加载更多，
 * 无数据展示没有数据界面
 * Created by 耿 on 2016/9/7.
 */
public abstract class RefreshLayout extends RelativeLayout {
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
    boolean showTopView = false;
    FooterLayout mLoadMoreView;
    public int LISTMODE = LISTMODE_LISTVIEW_OR_GRIDVIEW;
    int pageCount = 20;

    private void init() {
        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        addView(mSwipeRefreshLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mSwipeRefreshLayout.setColorSchemeColors(0xffff2500);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        mListView = new ListView(mContext);
        mListView.setDivider(new BitmapDrawable());
        mListView.setSelector(new BitmapDrawable());
        mListView.setDividerHeight(0);
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSwipeRefreshLayout.addView(mListView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    abstract NoDataLayout onCreateNoDataView();

    /*
    * 需指定AbsListView.LayoutParams
    *@date 2016/11/30 10:42
    */
    abstract FooterLayout onCreateLoadMoreView();

    /*
    * RelativeLayout.LayoutParams
    *@date 2016/11/30 10:42
    */
    abstract View onCreateTopView();

    /*
    * 如果有重试按钮，记得点击重试按钮隐藏失败界面和调用doRefresh();
    *@date 2016/11/30 10:42
    */
    abstract View onCreateFailureView();

    public void build() {
        mFailureView = onCreateFailureView();
        if (mFailureView == null) {
            mFailureView = LayoutInflater.from(mContext).inflate(R.layout.bad_network, null);
            mFailureView.setVisibility(View.GONE);
            mFailureView.findViewById(R.id.reload).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFailureView.setVisibility(View.GONE);
                    doRefresh();
                }
            });
        }
        addView(mFailureView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mNoDataView = onCreateNoDataView();
        if (mNoDataView == null) {
            mNoDataView = new SimpleNoDataLayout(mContext);
        }
        addView(mNoDataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mNoDataView.setVisibility(View.GONE);
        mLoadMoreView = onCreateLoadMoreView();
        if (mLoadMoreView == null) {
            mLoadMoreView = new FooterLoadingLayout(mContext);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(mContext, 40));
            mLoadMoreView.setAddLayoutParams(params);
        }
        mListView.addFooterView(mLoadMoreView.getFooterView(), null, false);

        mTopView = onCreateTopView();
        if (mTopView == null) {
            mTopView = new View(mContext);
            mTopView.setBackgroundResource(R.drawable.ic_menu_top);
            LayoutParams topLayoutParams = new LayoutParams(DensityUtils.dp2px(mContext, 40), DensityUtils.dp2px(mContext, 40));
            topLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            topLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            topLayoutParams.setMargins(0, 0, DensityUtils.dp2px(mContext, 16), DensityUtils.dp2px(mContext, 20));
            mTopView.setLayoutParams(topLayoutParams);
        }
        addView(mTopView);
        mTopView.setVisibility(View.GONE);
        mTopView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setSelection(0);
                mTopView.setVisibility(View.GONE);
            }
        });
    }

    public RefreshLayout refreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
        return this;
    }

    public RefreshLayout showTopView(boolean enable) {
        showTopView = enable;
        return this;
    }

    public RefreshLayout listMode(int mode) {
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
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setRefreshing(true);
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    //刷新完成添加列表数据
    public void refreshComplete(List list) {
        if (list.size() == 20) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
        adapter.appendList(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mNoDataView.setVisibility(View.GONE);
        if (list.size() == 0) {
            if (loadMoreEnable) {
                mLoadMoreView.onReset();
            }
            if (mNoDataView != null)
                mNoDataView.setVisibility(View.VISIBLE);
        }
    }

    //加载更多完成后添加数据
    public void loadMoreComplete(List list) {
        if (list.size() == 20) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
        adapter.addList(list);
        isLoadMore = false;
    }

    //数据请求失败调用
    public void loadFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (!isLoadMore) {
            adapter.getList().clear();
            adapter.notifyDataChanged();
            mFailureView.findViewById(R.id.bad_network).setVisibility(View.VISIBLE);
        } else {
            hasMoreData = false;
            isLoadMore = false;
            mLoadMoreView.onRefreshFailure();
        }

    }

    //是否允许控件加载更多
    public RefreshLayout loadMoreEnable(boolean b) {
        loadMoreEnable = b;
        return this;
    }

    public BAdapter getAdapter() {
        return this.adapter;
    }

    public List getList() {
        return this.adapter.getList();
    }

    //某次请求后。是否有更多数据需要加载
    public void hasMoreData(boolean b) {
        if (!loadMoreEnable)
            return;
        hasMoreData = b;
        isLoadMore = false;
        mLoadMoreView.onReset();
        if (!hasMoreData) {
            mLoadMoreView.onNoMoreData();
        } else {
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
    public RefreshLayout refresh(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        return this;
    }

    //添加listview滚动监听
    public RefreshLayout setOnListViewScrollListener(AbsListView.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
        return this;
    }

    //加载更多回调
    public RefreshLayout loadMore(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
        return this;
    }

    //设置适配器
    public RefreshLayout adapter(BAdapter adapter) {
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
