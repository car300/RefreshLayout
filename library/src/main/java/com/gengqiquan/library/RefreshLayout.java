package com.gengqiquan.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gengqiquan.library.utils.DensityUtils;
import com.gengqiquan.library.utils.ResourceUtil;
import com.sunshine.adapterlibrary.interfaces.RVBAdapter;
import com.sunshine.viewlibrary.refresh.FooterLoadingLayout;
import com.sunshine.viewlibrary.refresh.interfaces.FooterLayout;
import com.sunshine.viewlibrary.refresh.interfaces.LoadMoreListener;
import com.sunshine.viewlibrary.refresh.interfaces.RefreshListener;

import java.util.List;

/**
 * 以RecyclerView作为列表控件，
 * 集成了失败界面展示，
 * 点击重试按钮重试，
 * 下拉刷新，加载更多，
 * 无数据展示没有数据界面
 * 悬浮按钮
 * 万能适配器 适配器添加header和footer
 * TODO 控件上添加header
 * TODO 上滑隐藏header，下滑显示header
 * Created by 耿 on 2016/9/7.
 */
public class RefreshLayout extends RelativeLayout {
    public final static int LISTMODE_RECYCLER = 0;
    public final static int LISTMODE_LISTVIEW_OR_GRIDVIEW = 1;
    public final static LayoutParams RL_LPMM = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    Context mContext;
    RVBAdapter adapter;
    RefreshListener mRefreshListener;
    LoadMoreListener mLoadMoreListener;
    RecyclerView.OnScrollListener mScrollListener;
    AdapterView.OnItemClickListener mOnItemClickListener;
    RelativeLayout mFailureView;


    RelativeLayout mFloatView;
    RelativeLayout mEmptyView;
    boolean mIsLoadMore = false;
    boolean mHasMoreData = false;
    boolean mLoadMoreEnable = true;
    boolean mRefreshEnable = true;
    boolean mIsShowFloatView = false;
    FooterLayout mLoadMoreView;
    int mPageCount = 20;
    RecyclerView.LayoutManager mLayoutManager;


    @SuppressLint("InflateParams")
    private void init() {
        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        addView(mSwipeRefreshLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mSwipeRefreshLayout.setColorSchemeColors(0xffff2500);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsLoadMore = false;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });

        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mSwipeRefreshLayout.addView(mRecyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mFailureView = new RelativeLayout(mContext);
        addView(mFailureView, RL_LPMM);
        mFailureView.setVisibility(View.GONE);

        mEmptyView = new RelativeLayout(mContext);
        addView(mEmptyView, RL_LPMM);
        mEmptyView.setVisibility(View.GONE);

//        mLoadMoreView = onCreateLoadMoreView();
        if (mLoadMoreView == null) {
            mLoadMoreView = new FooterLoadingLayout(mContext);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(mContext, 40));
            mLoadMoreView.setAddLayoutParams(params);
        }

        mFloatView = new RelativeLayout(mContext);
        addView(mFloatView, RL_LPMM);
        mFloatView.setVisibility(View.GONE);
//        mFloatView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRecyclerView.setSelection(0);
//                mFloatView.setVisibility(View.GONE);
//            }
//        });

    }


    public RefreshLayout refreshEnable(boolean enable) {
        mRefreshEnable = enable;
        mSwipeRefreshLayout.setEnabled(mRefreshEnable);
        return this;
    }

    public RefreshLayout showFloatView(boolean enable) {
        mIsShowFloatView = enable;
        return this;
    }

    /**
     * 添加失败布局
     * 后门。如果用样式或者XML属性直接指定加载失败布局，可以通过制定重试按钮的ID为：reload 来实现点击重新加载
     *
     * @author gengqiquan
     * @date 2017/6/13 下午3:13
     */
    public RefreshLayout failureView(@NonNull View v) {
        mFailureView.removeAllViews();
        LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = RL_LPMM;
        }
        View reload = v.findViewById(ResourceUtil.getId(mContext, "reload"));
        if (reload != null) {
            reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFailureView.setVisibility(GONE);
                    doRefresh();
                }
            });
        }
        mFailureView.addView(v, layoutParams);
        return this;
    }

    @SuppressLint("InflateParams")
    public RefreshLayout failureView(@LayoutRes int res) {
        if (res != 0) {
            failureView(LayoutInflater.from(mContext).inflate(res, null));
        }
        return this;
    }

    public RefreshLayout emptyView(@NonNull View v) {
        mEmptyView.removeAllViews();
        LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = RL_LPMM;
        }
        mEmptyView.addView(v, layoutParams);
        return this;
    }

    @SuppressLint("InflateParams")
    public RefreshLayout emptyView(@LayoutRes int res) {
        if (res != 0) {
            emptyView(LayoutInflater.from(mContext).inflate(res, null));
        }
        return this;
    }

    @SuppressLint("InflateParams")
    public RefreshLayout floatView(@LayoutRes int res) {
        if (res != 0) {
            floatView(LayoutInflater.from(mContext).inflate(res, null));
        }
        return this;
    }

    public RefreshLayout floatView(@NonNull View v) {
        LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(DensityUtils.dp2px(mContext, 40), DensityUtils.dp2px(mContext, 40));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(mContext, 16), DensityUtils.dp2px(mContext, 20));

        }
        return floatView(v, layoutParams);
    }

    public RefreshLayout floatView(@NonNull View v, LayoutParams layoutParams) {
        mFloatView.removeAllViews();
        mFloatView.addView(v, layoutParams);
        return this;
    }

    public RelativeLayout getFloatView() {
        return mFloatView;
    }

    public RefreshLayout layoutManager(@NonNull RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter != null) {//刷新下适配器。在切换layoutManager
            mRecyclerView.setAdapter((RecyclerView.Adapter) adapter.getAdapter());
        }

        return this;
    }

    private RecyclerView.LayoutManager getDefaultLayoutManager(int layoutManagerType, int coulmn) {
        switch (layoutManagerType) {
            case 1:
                return new GridLayoutManager(mContext, coulmn);
            case 2:
                return new StaggeredGridLayoutManager(coulmn, StaggeredGridLayoutManager.VERTICAL);
            default:
                return new LinearLayoutManager(mContext);
        }
    }

    /*
     *分页数量
     *@author Administrator
     *@date 2016/10/22 16:35
     */
    public RefreshLayout pageSize(int number) {
        this.mPageCount = number;
        return this;
    }

    /*
     *手动调用刷新
     *@author 耿
     *@date 2016/9/8 15:06
     */
    public void doRefresh() {
//        mSwipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtils.dp2px(mContext, 10));
        mSwipeRefreshLayout.setRefreshing(true);
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    //刷新完成添加列表数据
    public void refreshComplete(List list) {
        adapter.appendList(list);
        if (list.size() >= mPageCount) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.setVisibility(View.GONE);
        if (list.size() == 0) {
            if (mLoadMoreEnable) {
                mLoadMoreView.onReset();
            }
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
                mLoadMoreView.hide();
            }
        }
    }

    //加载更多完成后添加数据
    public void loadMoreComplete(List list) {
        adapter.addList(list);
        if (list.size() >= mPageCount) {
            hasMoreData(true);
        } else {
            hasMoreData(false);
        }
        mIsLoadMore = false;
    }

    //数据请求失败调用
    public void failure() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (!mIsLoadMore) {
            if (!adapter.getList().isEmpty()) {//kotlin Empty clear 会蹦
                adapter.getList().clear();
            }
            adapter.notifyDataChanged();
            mFailureView.setVisibility(View.VISIBLE);
        } else {
            mHasMoreData = false;
            mIsLoadMore = false;
            mLoadMoreView.onRefreshFailure();
        }

    }

    //是否允许控件加载更多
    public RefreshLayout loadMoreEnable(boolean b) {
        mLoadMoreEnable = b;
        return this;
    }

    public RVBAdapter getAdapter() {
        return this.adapter;
    }

    /**
     * 获取列表控件
     *
     * @author gengqiquan
     * @date 2017/3/31 上午10:46
     */
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }


    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }


    public RelativeLayout getFailureView() {
        return mFailureView;
    }


    public RelativeLayout getEmptyView() {
        return mEmptyView;
    }

    /**
     * 列表项点击事件
     *
     * @author gengqiquan
     * @date 2017/3/31 上午10:43
     */
    public RefreshLayout setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public List getList() {
        return this.adapter.getList();
    }

    //某次请求后。是否有更多数据需要加载
    public void hasMoreData(boolean b) {
        if (!mLoadMoreEnable)
            return;
        mHasMoreData = b;
        mIsLoadMore = false;
        mLoadMoreView.onReset();
        if (!mHasMoreData) {
            if (getList().size() > 0) {//无数据不显示foot
                mLoadMoreView.onNoMoreData();
            }
            return;
        } else {
            mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (mLoadMoreEnable && mHasMoreData && !mIsLoadMore) {
                        if (newState == ListView.OnScrollListener.SCROLL_STATE_IDLE
                                || newState == ListView.OnScrollListener.SCROLL_STATE_FLING) {
                            if (isLastItemVisible()) {
                                if (mLoadMoreListener != null) {
                                    mLoadMoreListener.LoadMore();
                                    mLoadMoreView.onRefreshing();
                                    mIsLoadMore = true;
                                }
                            }
                        }
                    }
                    if (mIsShowFloatView) {
                        switch (newState) {
                            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                                mFloatView.setVisibility(View.GONE);
                                break;
                            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                                if (findFirstVisibleItemPosition() > 0)
                                    mFloatView.setVisibility(View.VISIBLE);
                                else
                                    mFloatView.setVisibility(View.GONE);
                                break;
                        }
                    }
                    if (null != mScrollListener) {
                        mScrollListener.onScrollStateChanged(recyclerView, newState);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (null != mScrollListener) {
                        mScrollListener.onScrolled(recyclerView, dx, dy);
                    }
                }
            });
        }
    }

    private int findFirstVisibleItemPosition() {
        if (mLayoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
        }
        if (mLayoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
        }
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPositions(null)[0];
        }
        throw new IllegalArgumentException(mLayoutManager.getClass().getName() + " must be one of "
                + " LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager can findFirstCompletelyVisibleItemPosition");
    }

    private int findLastVisibleItemPosition() {
        if (mLayoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
        }
        if (mLayoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
        }
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] positions = ((StaggeredGridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPositions(null);
            int max = -1;
            // 多列时,最后一个item不一定是最左边一列
            for (int position : positions) {
                max = Math.max(max, position);
            }
            return max;
        }
        throw new IllegalArgumentException(mLayoutManager.getClass().getName() + " must be one of "
                + " LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager can findLastCompletelyVisibleItemPositions");
    }

    private boolean isLastItemVisible() {
        final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();

        if (null == adapter || adapter.getItemCount() == 0) {
            return true;
        }

        final int lastItemPosition = adapter.getItemCount() - 1;
        final int lastVisiblePosition = findLastVisibleItemPosition();

        /**
         * This check should really just be: lastVisiblePosition ==
         * lastItemPosition, but ListView internally uses a FooterView which
         * messes the positions up. For me we'll just subtract one to account
         * for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - findFirstVisibleItemPosition();
            final int childCount = mRecyclerView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mRecyclerView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mRecyclerView.getBottom();
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
    public RefreshLayout setOnListViewScrollListener(RecyclerView.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
        return this;
    }

    //加载更多回调
    public RefreshLayout loadMore(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
        return this;
    }

    //设置适配器
    public RefreshLayout adapter(RVBAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter((RecyclerView.Adapter) adapter.getAdapter());
        adapter.addFooterView(mLoadMoreView.getFooterView());
        return this;
    }

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.RefreshLayoutBase);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RefreshLayout);
        mRefreshEnable = a.getBoolean(R.styleable.RefreshLayout_refreshEnable, true);
        mLoadMoreEnable = a.getBoolean(R.styleable.RefreshLayout_loadMoreEnable, true);
        mIsShowFloatView = a.getBoolean(R.styleable.RefreshLayout_showFloatView, true);
        mPageCount = a.getInteger(R.styleable.RefreshLayout_pageCount, 20);
        int coulmnNum = a.getInteger(R.styleable.RefreshLayout_column, 1);
        int layoutManagerType = a.getInteger(R.styleable.RefreshLayout_recyclerViewLayoutManager, 0);
        failureView(a.getResourceId(R.styleable.RefreshLayout_failureLayout, 0));
        emptyView(a.getResourceId(R.styleable.RefreshLayout_emptyLayout, 0));
        floatView(a.getResourceId(R.styleable.RefreshLayout_floatView, 0));
        a.recycle();


        layoutManager(getDefaultLayoutManager(layoutManagerType, coulmnNum));
        refreshEnable(mRefreshEnable);

    }
}
