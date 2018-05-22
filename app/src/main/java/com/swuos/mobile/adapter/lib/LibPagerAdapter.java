package com.swuos.mobile.adapter.lib;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.swuos.mobile.R;
import com.swuos.mobile.ui.lib.BaseLibPageView;
import com.swuos.mobile.widgets.LibPageViewFactory;

/**
 * Created by youngkaaa on 2018/5/19
 */
public class LibPagerAdapter extends PagerAdapter {

    private Context context;

    public LibPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int mode = position == 1 ? LibPageViewFactory.LIB_BOOKSHELF : LibPageViewFactory.LIB_POPULAR;
        BaseLibPageView page = LibPageViewFactory.create(container.getContext(), mode);
        if (page == null) {
            return null;
        }
        container.addView(page.getView());
        return page.getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int id = position == 0 ? R.string.lib_popular_books : R.string.lib_my_bookshelf;
        return context.getString(id);
    }
}
