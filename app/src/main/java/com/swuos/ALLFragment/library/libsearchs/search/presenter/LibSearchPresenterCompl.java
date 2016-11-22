package com.swuos.ALLFragment.library.libsearchs.search.presenter;

import android.content.Context;

import com.swuos.ALLFragment.library.libsearchs.search.interfacecompl.search.LibSearchApi;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;
import com.swuos.ALLFragment.library.libsearchs.search.model.util.Parse;
import com.swuos.ALLFragment.library.libsearchs.search.view.ILibSearchView;
import com.swuos.swuassistant.Constant;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by 张孟尧 on 2016/9/5.
 */

public class LibSearchPresenterCompl implements ILibSearchPresenter {
    private Context context;
    private ILibSearchView libSearchview;
    private List<SearchBookItem> searchBookItemList = new ArrayList<>();
    private String bookname;
    private Subscriber moreSubscriber;
    private Subscriber fitstSubscriber;
    private int checkoutSearch = 0;
    private int allBookSize = 0;

    public LibSearchPresenterCompl(Context context, ILibSearchView iLibSearchView) {
        this.context = context;
        this.libSearchview = iLibSearchView;


    }

    public List<SearchBookItem> getSearchBookItemList() {
        return searchBookItemList;
    }


    public void SearchMore(int currentPage) {
        Map<String, String> map = new HashMap<>();
        map.put("q", bookname);
        map.put("flword", bookname);
        map.put("searchType", "oneSearch");
        map.put("field", "title");
        map.put("searchModel", "seg");
        map.put("oneSearchWord", "title" + bookname);
        map.put("twoSearchWord", "");
        map.put("advSearchWold", "");
        map.put("combineSearchWold", "");
        map.put("exactSearch", "yes");
        map.put("corename", "");
        map.put("gcbook", "yes");
        map.put("pager.offset", String.valueOf(currentPage * 10));
        moreSubscriber = new Subscriber<SearchResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                String error = throwable.getMessage();
                if (throwable instanceof UnknownHostException) {
                    error = Constant.CLIENT_ERROR;
                } else if (throwable instanceof SocketTimeoutException) {
                    error = Constant.CLIENT_TIMEOUT;
                }

                libSearchview.ShowError(error);
            }

            @Override
            public void onNext(SearchResult searchResult) {
                searchBookItemList.addAll(searchResult.getSearchbookItemList());
                libSearchview.ShowMore(searchResult);
            }
        };

        LibSearchApi.getLibSearch().searchBook(map).flatMap(new Func1<String, Observable<List<SearchBookItem>>>() {
            @Override
            public Observable<List<SearchBookItem>> call(String s) {
                return Observable.just(Parse.getSearchResult(s));
            }
        }).concatMap(new Func1<List<SearchBookItem>, Observable<SearchResult>>() {
                    @Override
                    public Observable<SearchResult> call(List<SearchBookItem> searchBookItems) {
                        SearchResult s = new SearchResult();
                        s.setBookSize(searchBookItems.get(0).getSearchResultNum());
                        s.setSearchbookItemList(searchBookItems);
                        return Observable.just(s);
                    }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(moreSubscriber);

    }


    public void firstSearch(String bookName) {
        this.bookname = bookName;
        fitstSubscriber = new Subscriber<SearchResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                String error = throwable.getMessage();
                if (throwable instanceof UnknownHostException) {
                    error = Constant.CLIENT_ERROR;
                } else if (throwable instanceof SocketTimeoutException) {
                    error = Constant.CLIENT_TIMEOUT;
                }

                libSearchview.ShowError(error);
            }

            @Override
            public void onNext(SearchResult searchResult) {
                searchBookItemList.clear();
                searchBookItemList.addAll(searchResult.getSearchbookItemList());
                libSearchview.ShowResult(searchResult);
            }
        };

        Map<String, String> map = new HashMap<>();
        map.put("q", bookName);
        map.put("flword", bookName);
        map.put("searchType", "oneSearch");
        map.put("field", "title");
        map.put("searchModel", "seg");
        map.put("oneSearchWord", "title" + bookName);
        map.put("twoSearchWord", "");
        map.put("advSearchWold", "");
        map.put("combineSearchWold", "");
        map.put("exactSearch", "yes");
        map.put("corename", "");
        map.put("gcbook", "yes");
        map.put("pager.offset", "0");
        LibSearchApi.getLibSearch().searchBook(map).flatMap(new Func1<String, Observable<List<SearchBookItem>>>() {
            @Override
            public Observable<List<SearchBookItem>> call(String s) {
                return Observable.just(Parse.getSearchResult(s));
            }
        }).concatMap(new Func1<List<SearchBookItem>, Observable<SearchResult>>() {
            @Override
            public Observable<SearchResult> call(List<SearchBookItem> searchBookItems) {
                SearchResult s = new SearchResult();
                s.setBookSize(searchBookItems.get(0).getSearchResultNum());
                s.setSearchbookItemList(searchBookItems);
                return Observable.just(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(fitstSubscriber);
    }

    public void cancelSearch() {
        if (moreSubscriber != null && moreSubscriber.isUnsubscribed()) {
            moreSubscriber.unsubscribe();
        }
        if (fitstSubscriber != null && fitstSubscriber.isUnsubscribed()) {
            fitstSubscriber.unsubscribe();
        }
    }


    public void checkoutSearch(int i) {
        checkoutSearch = i;
    }


    public int getCheckoutSearch() {
        return checkoutSearch;
    }


}
