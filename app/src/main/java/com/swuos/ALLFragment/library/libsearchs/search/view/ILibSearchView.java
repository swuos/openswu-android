package com.swuos.ALLFragment.library.libsearchs.search.view;


import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/5.
 */

public interface ILibSearchView {
    void ShowResult(SearchResult searchResult);
    void ShowMore(SearchResult searchResult);

    void ShowError(String message);

}
