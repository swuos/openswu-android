package com.swuos.ALLFragment.library.libsearchs.search.presenter;

import android.content.Context;
import android.util.Log;

import com.swuos.ALLFragment.library.libsearchs.search.api.LibSearchApi;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchBookItem;
import com.swuos.ALLFragment.library.libsearchs.search.model.bean.SearchResult;
import com.swuos.ALLFragment.library.libsearchs.search.util.ParserUtil;
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
    private static final String TAG = "LibSearchPresenterCompl";
    private Context context;
    private ILibSearchView libSearchview;
    private List<SearchBookItem> searchBookItemList = new ArrayList<>();
    private String bookname;
    private Subscriber subscriber1;
    private Subscriber subscriber2;
    private int checkoutSearch = 0;
    private int allBookSize = 0;

    public LibSearchPresenterCompl(Context context, ILibSearchView iLibSearchView) {
        this.context = context;
        this.libSearchview = iLibSearchView;


    }

    public List<SearchBookItem> getSearchBookItemList() {
        return searchBookItemList;
    }

    @Override
    public void SearchMore(int currentPage) {
        int currentPos = 20 * (currentPage - 1);
        subscriber1 = new Subscriber<SearchResult>() {
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
        Map<String, String> map = new HashMap<>();
        map.put("q", this.bookname);
        map.put("flword", this.bookname);
        map.put("searchType", "oneSearch");
        map.put("field", "title");
        map.put("searchModel", "seg");
        map.put("oneSearchWord", "title" + this.bookname);
        map.put("twoSearchWord", "");
        map.put("advSearchWold", "");
        map.put("combineSearchWold", "");
        map.put("exactSearch", "yes");
        map.put("corename", "");
        map.put("gcbook", "yes");
        map.put("pager.offset", String.valueOf(currentPos));
        LibSearchApi.getLibSearch().searchBook(map).flatMap(new Func1<String, Observable<SearchBookItem>>() {
            @Override
            public Observable<SearchBookItem> call(String s) {
                Observable<SearchBookItem> from = Observable.from(ParserUtil.getSearchResult(s));
                return from;
            }
        }).concatMap(new Func1<SearchBookItem, Observable<SearchBookItem>>() {
            @Override
            public Observable<SearchBookItem> call(final SearchBookItem searchBookItem) {
                return LibSearchApi.getLibSearch().getBookstateNum(searchBookItem.getBookId(), System.currentTimeMillis()).flatMap(new Func1<String, Observable<SearchBookItem>>() {
                    @Override
                    public Observable<SearchBookItem> call(String s) {
                        String[] nums = s.split("/");
                        Log.d(TAG, "nums[0]=>" + nums[0]);
                        Log.d(TAG, "nums[1]=>" + nums[1]);
                        searchBookItem.setBookNumber(nums[0]);
                        searchBookItem.setLendableNumber(nums[1]);
                        return Observable.just(searchBookItem);
                    }
                });
            }
        }).buffer(10).map(new Func1<List<SearchBookItem>, SearchResult>() {
            @Override
            public SearchResult call(List<SearchBookItem> searchBookItems) {
                SearchResult result = new SearchResult();
                result.setBookSize(searchBookItems.size());
                result.setSearchbookItemList(searchBookItems);
                for (SearchBookItem item : result.getSearchbookItemList()) {
                    Log.d(TAG, "bookId==>" + item.getBookId());
                }
                return result;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);

    }

    @Override
    public void firstSearch(String bookName) {
        this.bookname = bookName;
        subscriber2 = new Subscriber<SearchResult>() {
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
                Log.d(TAG, throwable.getMessage());
                throwable.printStackTrace();
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
        LibSearchApi.getLibSearch().searchBook(map).flatMap(new Func1<String, Observable<SearchBookItem>>() {
            @Override
            public Observable<SearchBookItem> call(String s) {
                Observable<SearchBookItem> from = Observable.from(ParserUtil.getSearchResult(s));
                return from;
            }
        }).concatMap(new Func1<SearchBookItem, Observable<SearchBookItem>>() {
            @Override
            public Observable<SearchBookItem> call(final SearchBookItem searchBookItem) {
                return LibSearchApi.getLibSearch().getBookstateNum(searchBookItem.getBookId(), System.currentTimeMillis()).flatMap(new Func1<String, Observable<SearchBookItem>>() {
                    @Override
                    public Observable<SearchBookItem> call(String s) {
                        String[] nums = s.split("/");

                        Log.d("kklog", "nums[0]=>" + nums[0]);
                        Log.d("kklog", "nums[1]=>" + nums[1]);
                        searchBookItem.setBookNumber(nums[0]);
                        searchBookItem.setLendableNumber(nums[1]);
                        return Observable.just(searchBookItem);
                    }
                });
            }
        }).buffer(10).map(new Func1<List<SearchBookItem>, SearchResult>() {
            @Override
            public SearchResult call(List<SearchBookItem> searchBookItems) {
                SearchResult result = new SearchResult();
                result.setBookSize(searchBookItems.size());
                result.setSearchbookItemList(searchBookItems);
                return result;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber2);
    }

    @Override
    public void cancelSearch() {
        if (subscriber1 != null && subscriber1.isUnsubscribed()) {
            subscriber1.unsubscribe();
        }
        if (subscriber2 != null && subscriber2.isUnsubscribed()) {
            subscriber2.unsubscribe();
        }
    }

    @Override
    public void checkoutSearch(int i) {
        checkoutSearch = i;
    }

    @Override
    public int getCheckoutSearch() {
        return checkoutSearch;
    }


}
