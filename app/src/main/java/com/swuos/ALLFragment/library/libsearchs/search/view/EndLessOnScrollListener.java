package com.swuos.ALLFragment.library.libsearchs.search.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.swuos.ALLFragment.library.libsearchs.search.adapter.SearchRecycleAdapter;


public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    private int currentPage = 1;
    //已经加载出来的Item的数量
    private int totalItemCount;

    //主要用来存储上一个totalItemCount
    private int previousTotal = 0;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    private int firstVisibleItem;

    //是否正在上拉数据
    private boolean loading = true;
    private SearchRecycleAdapter searchRecycleAdapter;

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager, SearchRecycleAdapter searchRecycleAdapter) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.searchRecycleAdapter = searchRecycleAdapter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount() - 1;
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (loading) {


            if (totalItemCount > previousTotal) {
                //说明数据已经加载结束
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        //这里需要好好理解
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem && searchRecycleAdapter.ismOpenLoadMore()) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    /**
     * 在加载另外的搜索结果时清除状态
     */
    public void clean() {
        previousTotal = 0;
        currentPage = 1;
        loading = true;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public abstract void onLoadMore(int currentPage);
}