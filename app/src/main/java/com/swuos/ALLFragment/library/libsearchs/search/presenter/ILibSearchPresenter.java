package com.swuos.ALLFragment.library.libsearchs.search.presenter;


import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/5.
 */

public interface ILibSearchPresenter {
    void SearchMore(int currentPage);

    void firstSearch(String bookName);

    void cancelSearch();

    void checkoutSearch(int i);
    int getCheckoutSearch();

    List<SearchBookItem> getSearchBookItemList();
}
