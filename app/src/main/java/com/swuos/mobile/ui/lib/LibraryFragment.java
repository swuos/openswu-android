package com.swuos.mobile.ui.lib;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gallops.mobile.jmvclibrary.app.BaseFragment;
import com.jianyuyouhun.inject.annotation.FindViewById;
import com.swuos.mobile.R;
import com.swuos.mobile.adapter.lib.LibPagerAdapter;
import com.swuos.mobile.utils.DisplayUtils;
import com.swuos.mobile.widgets.RoundSearchView;

import java.lang.reflect.Field;

/**
 * 图书馆主页
 * Created by wangyu on 2018/3/7.
 */

public class LibraryFragment extends BaseFragment {

    @FindViewById(R.id.linear_lib_search_root)
    private View mSearchRoot;
    @FindViewById(R.id.tab_lib)
    private TabLayout mTabLayout;
    @FindViewById(R.id.vp_lib)
    private ViewPager mViewPager;


    private RoundSearchView mSearchView;
    private LibPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.f_library;
    }

    @Override
    protected void onCreateView(View rootView, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        mSearchView = new RoundSearchView(mSearchRoot);
        String hint = getString(R.string.lib_search_hint);
        mSearchView.setHintMode(hint);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.lib_popular_books));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.lib_my_bookshelf));
        mPagerAdapter = new LibPagerAdapter(getContext());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mPagerAdapter);
        setUpIndicatorWidth(mTabLayout, 44, 44);
    }


    /**
     * 通过反射修改TabLayout底部indicator宽度 参考自网上
     *
     * @param tabLayout
     * @param marginLeft
     * @param marginRight
     */
    private void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            LinearLayout layout = null;
            if (tabStrip != null) {
                tabStrip.setAccessible(true);
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart((int) DisplayUtils.dip2px(marginLeft));
                    params.setMarginEnd((int) DisplayUtils.dip2px(marginRight));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
