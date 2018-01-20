package com.swuos.mobile.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.swuos.mobile.utils.injector.ModelInjector;

/**
 * fragment基类
 * Created by wangyu on 2017/12/7.
 */

public abstract class BaseFragment extends Fragment {
    private boolean isDestroy = false;
    private long mInsertDt = System.currentTimeMillis();
    protected String TAG;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        isDestroy = false;
        ModelInjector.injectModel(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public Context getContext() {
        return getBaseActivity();
    }

    public long getInsertDt() {
        return mInsertDt;
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }

    public void showProgressDialog(String message) {
        if (isDestroy) return;
        getBaseActivity().showProgressDialog(message);
    }

    public void dismissProgressDialog() {
        if (isDestroy) return;
        getBaseActivity().dismissProgressDialog();
    }

    /**
     * 启动页面
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getBaseActivity(), cls));
    }

    /**
     * 启动页面
     */
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(getBaseActivity(), cls), requestCode);
    }
}
