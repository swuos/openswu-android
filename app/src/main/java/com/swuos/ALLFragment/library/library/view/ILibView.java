package com.swuos.ALLFragment.library.library.view;

import com.swuos.ALLFragment.library.library.model.BookItem;
import com.swuos.ALLFragment.library.library.view.base.IBaseView;

import java.util.List;


/**
 * Created by : youngkaaa on 2016/10/25.
 * Contact me : 645326280@qq.com
 */

public interface ILibView extends IBaseView {
    void updateData(List<BookItem> bookItems);
    void hindFab();
    void showFab();
    void showRefreshLayout();
    void hideRefreshLayout();
}
