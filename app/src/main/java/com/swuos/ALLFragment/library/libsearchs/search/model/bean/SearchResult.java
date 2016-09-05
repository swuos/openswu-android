package com.swuos.ALLFragment.library.libsearchs.search.model.bean;


import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/6.
 */
public class SearchResult {
    private int bookSize;
    private List<SearchBookItem> searchbookItemList;

    public int getBookSize() {
        return bookSize;
    }

    public void setBookSize(int bookSize) {
        this.bookSize = bookSize;
    }

    public List<SearchBookItem> getSearchbookItemList() {
        return searchbookItemList;
    }

    public void setSearchbookItemList(List<SearchBookItem> searchbookItemList) {
        this.searchbookItemList = searchbookItemList;
    }
}
