package com.swuos.mobile.ui.lib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.gallops.mobile.jmvclibrary.http.OnResultListener;
import com.swuos.mobile.adapter.lib.LibBookshelfAdapter;
import com.swuos.mobile.entity.LibBookshelfItem;
import com.swuos.mobile.models.http.requester.LibBookshelfRequest;
import com.swuos.mobile.utils.L;

import java.util.List;

/**
 * Created by youngkaaa on 2018/5/19
 */
public class LibBookshelfPageView extends BaseLibPageView implements OnResultListener<List<LibBookshelfItem>> {
    private LibBookshelfRequest mRequest;
    private LibBookshelfAdapter mAdapter;

    public LibBookshelfPageView(Context context) {
        super(context);
    }

    @Override
    protected void initCache() {
        List<LibBookshelfItem> items = mCacheManager.getCacheBookshelfList();
        // 无网 且没有缓存
        if (items == null || items.isEmpty()) {
            // todo show loading?
            return;
        }
        setData(items);
    }

    @Override
    protected void initData() {
        // todo 临时测试写死 发版记得修改
        String swuId = "222014321210029";
        mRequest = new LibBookshelfRequest(getContext(), swuId, this);
        mRequest.execute();
        showLoading(false);
    }

    @Override
    public void onResult(int code, List<LibBookshelfItem> items, String msg) {
        L.d("code->" + code + ",items->" + items + ",msg->" + msg);
        stopRefresh();
        setData(items);
    }

    private void setData(List<LibBookshelfItem> items) {
        if (mAdapter == null) {
            mAdapter = new LibBookshelfAdapter(getContext());
        }
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(mAdapter);
        }
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        mAdapter.setData(items);
        showListView();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (mRequest != null) {
            mRequest.execute();
        } else {
            stopRefresh();
        }
    }
}
