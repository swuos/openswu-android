package com.swuos.ALLFragment.library.lib.views;




import com.swuos.ALLFragment.library.lib.model.BookBean2;

import java.util.List;

/**
 * Created by youngkaaa on 2016/5/27.
 * Email:  645326280@qq.com
 */
public interface ILibView {
    void onSetRecyclerViewVisible(int visible);
    void onSetProgressDialogVisible(int visible);
    void onSetSwipeRefreshVisible(int visible);
    void onUpdateBookItems(int code, List<BookBean2> bookItems);
    void onSetTipDialogVisible(int visible);
    void onSetErrorLayoutVisible(int visible);
}
