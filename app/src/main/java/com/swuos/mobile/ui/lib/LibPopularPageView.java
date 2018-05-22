package com.swuos.mobile.ui.lib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.swuos.mobile.adapter.lib.LibPopularAdapter;
import com.swuos.mobile.entity.LibPopularItem;
import com.swuos.mobile.models.http.requester.LibPopularRequest;
import com.swuos.mobile.utils.L;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/18
 */
public class LibPopularPageView extends BaseLibPageView implements OnResultListener<List<LibPopularItem>> {
    private LibPopularRequest mRequest;

    private List<LibPopularItem> mItems;
    private LibPopularAdapter mAdapter;

    public LibPopularPageView(Context context) {
        super(context);
    }

    @Override
    protected void initCache() {
        // 先设置缓存
        List<LibPopularItem> items = mCacheManager.getCachePopularList();
        if (items == null || items.isEmpty()) {
            //todo show loading?
            return;
        }
        setData(items, false);
    }

    @Override
    protected void initData() {
        mRequest = new LibPopularRequest(getContext(), this);
        mRequest.execute();
        showLoading(false);
    }

    @Override
    public void onResult(int code, List<LibPopularItem> libPopularItems, String msg) {
        L.d("code->" + code + ",items->" + libPopularItems + ",msg->" + msg);
        boolean autoApend = true;
        // 第一次 清空之前的cache
        L.d("mRequest.getPage() ->" + mRequest.getPage());
        // 如果是第一页 就不拼接  反之需要拼接数据
        if (mRequest.getPage() == 1) {
            stopRefresh();
            autoApend = false;
        }
        setData(libPopularItems, autoApend);
    }

    private void setData(List<LibPopularItem> items, boolean autoApend) {
        if (mAdapter == null) {
            mAdapter = new LibPopularAdapter(getContext());
        }
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mAdapter);
        }
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mAdapter.setData(items, autoApend);
        showListView();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        // 重置页码
        mRequest.reset();
        mRequest.execute();
    }
}
