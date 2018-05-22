package com.swuos.mobile.widgets;

import android.content.Context;
import android.util.SparseArray;

import com.swuos.mobile.app.GlideApp;
import com.swuos.mobile.ui.lib.BaseLibPageView;
import com.swuos.mobile.ui.lib.LibBookshelfPageView;
import com.swuos.mobile.ui.lib.LibPopularPageView;
import com.swuos.mobile.ui.lib.LibSearchPageView;

/**
 * Created by youngkaaa on 2018/5/19
 */
public class LibPageViewFactory {
    public static final int LIB_POPULAR = 1;
    public static final int LIB_BOOKSHELF = 2;
    public static final int LIB_SEARCH = 3;

    // private
    private LibPageViewFactory() {
    }

    public static BaseLibPageView create(Context context, int mode) {
        BaseLibPageView pageView = null;
        switch (mode) {
            case LIB_POPULAR:
                pageView = new LibPopularPageView(context);
                break;
            case LIB_BOOKSHELF:
                pageView = new LibBookshelfPageView(context);
                break;
            case LIB_SEARCH:
                pageView = new LibSearchPageView(context);
                break;
            default:
                break;
        }
        return pageView;
    }
}
