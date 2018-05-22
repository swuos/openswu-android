package com.swuos.mobile.ui.lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.gallops.mobile.jmvclibrary.app.BaseActivity;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.jianyuyouhun.inject.annotation.OnClick;
import com.swuos.mobile.R;
import com.swuos.mobile.utils.DisplayUtils;
import com.swuos.mobile.utils.L;
import com.swuos.mobile.widgets.LibPageViewFactory;
import com.swuos.mobile.widgets.RoundSearchView;

/**
 * 图书搜索页
 * Created by youngkaaa on 2018/5/21
 */
public class LibSearchActivity extends BaseActivity implements TextWatcher {
    @FindViewById(R.id.iv_lib_search_back)
    View mBtnBack;
    @FindViewById(R.id.linear_lib_search_root)
    private View mSearchRoot;
    @FindViewById(R.id.tv_lib_search)
    View mBtnSearch;
    @FindViewById(R.id.frame_lib_search_container)
    ViewGroup mResultContainer;

    private RoundSearchView mSearchView;
    private BaseLibPageView mResultView;

    @Override
    protected int getLayoutResId() {
        return R.layout.a_lib_search;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mSearchView = new RoundSearchView(mSearchRoot);
        mSearchView.setTextChangeListener(this);
        // 里面会做默认提示
        mSearchView.setSearchMode("");

        mResultView = LibPageViewFactory.create(this, LibPageViewFactory.LIB_SEARCH);
        mResultContainer.removeAllViews();
        ViewGroup parent = (ViewGroup) mResultView.getView().getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mResultContainer.addView(mResultView.getView());
    }

    @OnClick({R.id.iv_lib_search_back, R.id.tv_lib_search})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_lib_search_back:
                finish();
                break;
            case R.id.tv_lib_search:
                doSearch();
                break;
            default:
                break;
        }
    }

    private void doSearch() {
        if (mSearchView == null || mResultView == null) {
            // todo toast?
            return;
        }
        String input = mSearchView.getInput();
        L.d("input->" + input);
        if (TextUtils.isEmpty(input)) {
            // TODO: 2018/5/21 toast???
            return;
        }
        if (!(mResultView instanceof LibSearchPageView)) {
            // TODO: 2018/5/21 toast???
            return;
        }
        ((LibSearchPageView) mResultView).search(input);
        // edittext在顶部搜索框中
        mSearchView.onSearch();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == null || mBtnSearch == null) {
            return;
        }
        String string = s.toString();
        if (TextUtils.isEmpty(string)) {
            mBtnSearch.setVisibility(View.GONE);
        } else {
            mBtnSearch.setVisibility(View.VISIBLE);
        }
    }
}
