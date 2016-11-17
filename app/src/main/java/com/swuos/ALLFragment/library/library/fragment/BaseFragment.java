package com.swuos.ALLFragment.library.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */

public abstract class BaseFragment extends Fragment {
    private View mRootView;

    public abstract int getLayoutId();
    public abstract void butterBind();
    public abstract void initData();
    public abstract void checkNetWork();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=inflater.inflate(getLayoutId(),container,false);
        butterBind();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkNetWork();
        initData();
    }

    public View getRootView() {
        return mRootView;
    }
}
