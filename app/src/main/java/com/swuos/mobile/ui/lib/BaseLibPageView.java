package com.swuos.mobile.ui.lib;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swuos.mobile.R;
import com.swuos.mobile.utils.LibCacheManager;
import com.swuos.mobile.utils.NetworkUtils;

/**
 * Created by youngkaaa on 2018/5/18
 */
public abstract class BaseLibPageView implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    protected View mRootView;
    protected RecyclerView mRecyclerView;
    protected View mErrorRoot;
    protected ImageView mIvErrorIcon;
    protected TextView mTvErrorDesc;
    protected View mFrameProgressLayout;
    private SwipeRefreshLayout mRefreshLayout;

    protected LibCacheManager mCacheManager;

    public BaseLibPageView(Context context) {
        if (context == null) {
            return;
        }
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.v_lib_page, null);
        mCacheManager = LibCacheManager.instance(context);

        initView();
        // 目前只有在无网时才加载缓存的数据
        if (!NetworkUtils.isAvailable(getContext())) {
            initCache();
        }
        initData();
    }


    private void initView() {
        if (mRootView == null) {
            return;
        }
        mRecyclerView = mRootView.findViewById(R.id.recycler_lib_page);
        mRefreshLayout = mRootView.findViewById(R.id.refresh_lib);
        mErrorRoot = mRootView.findViewById(R.id.linear_lib_page_error);
        mIvErrorIcon = mErrorRoot.findViewById(R.id.iv_lib_page_error);
        mTvErrorDesc = mErrorRoot.findViewById(R.id.tv_lib_page_error);
        mFrameProgressLayout = mRootView.findViewById(R.id.frame_progress_layout);

        mRefreshLayout.setOnRefreshListener(this);
    }

    // 加载缓存数据
    protected abstract void initCache();
    // 正常处理网络数据等
    protected abstract void initData();

    /**
     * 显示错误页 同时会隐藏掉列表和loading
     *
     * @param drawable 错误页图片资源
     * @param desc     错误页提示文字
     */
    protected void showErrorView(@DrawableRes int drawable, String desc) {
        if (mErrorRoot != null) {
            mErrorRoot.setVisibility(View.VISIBLE);
        }
        if (mFrameProgressLayout != null) {
            mFrameProgressLayout.setVisibility(View.GONE);
        }
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.GONE);
        }
        if (mIvErrorIcon != null) {
            mIvErrorIcon.setImageResource(drawable);
        }
        if (mTvErrorDesc != null) {
            mTvErrorDesc.setText(desc);
        }
    }

    /**
     * 展示列表
     */
    protected void showListView() {
        if (mErrorRoot != null) {
            mErrorRoot.setVisibility(View.GONE);
        }
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        if (mFrameProgressLayout != null) {
            mFrameProgressLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 展示loading
     * @param needHide 是否需要隐藏掉后面的列表
     */
    protected void showLoading(boolean needHide) {
        if (mErrorRoot != null) {
            mErrorRoot.setVisibility(View.GONE);
        }
        if (needHide) {
            if (mRecyclerView != null) {
                mRecyclerView.setVisibility(View.GONE);
            }
        }
        if (mFrameProgressLayout != null) {
            mFrameProgressLayout.setVisibility(View.VISIBLE);
        }
    }

    public View getView() {
        return mRootView;
    }

    public Context getContext() {
        return context;
    }

    // 禁用掉下拉刷新
    public void unableRefresh(){
        mRefreshLayout.setEnabled(false);
    }

    /**
     * 子类去实现对应的刷新时操作
     */
    @Override
    public void onRefresh() {

    }

    /**
     * 停止刷新loading
     */
    protected void stopRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
