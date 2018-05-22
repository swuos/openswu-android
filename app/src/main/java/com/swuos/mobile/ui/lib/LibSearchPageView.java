package com.swuos.mobile.ui.lib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.swuos.mobile.adapter.lib.LibPopularAdapter;
import com.swuos.mobile.adapter.lib.LibSearchAdapter;
import com.swuos.mobile.entity.LibPopularItem;
import com.swuos.mobile.entity.LibSearchItem;
import com.swuos.mobile.models.http.requester.LibPopularRequest;
import com.swuos.mobile.models.http.requester.LibSearchRequest;
import com.swuos.mobile.utils.L;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/18
 */
public class LibSearchPageView extends BaseLibPageView implements OnResultListener<List<LibSearchItem>> {
    private LibSearchRequest mRequest;

    private List<LibSearchItem> mItems;
    private LibSearchAdapter mAdapter;

    public LibSearchPageView(Context context) {
        super(context);
    }

    @Override
    protected void initCache() {
        // 搜索页不需要读缓存 也没有缓存
    }

    @Override
    protected void initData() {
        mRequest = new LibSearchRequest(getContext(), this);
        // 禁用掉下拉刷新
        unableRefresh();
    }

    @Override
    public void onResult(int code, List<LibSearchItem> items, String msg) {
        L.d("code->" + code + ",items->" + items + ",msg->" + msg);
        setData(items);
    }

    /**
     * 搜索指定图书
     * @param name 非空判断在request里面会判断
     */
    public void search(String name) {
        showLoading(false);
        mRequest.search(name);
    }

    private void setData(List<LibSearchItem> items) {
        if (mAdapter == null) {
            mAdapter = new LibSearchAdapter();
        }
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mAdapter);
        }
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mItems = items;
        mAdapter.setData(items);
        showListView();
    }
}
