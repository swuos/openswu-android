package com.swuos.ALLFragment.library.library.presenter.Imp;

import android.content.Context;
import android.util.Log;

import com.swuos.ALLFragment.library.library.api.manager.ServiceManager;
import com.swuos.ALLFragment.library.library.model.BookItem;
import com.swuos.ALLFragment.library.library.presenter.ILibPresenter;
import com.swuos.ALLFragment.library.library.utils.Parser;
import com.swuos.ALLFragment.library.library.utils.SharedPreferenceUtil;
import com.swuos.ALLFragment.library.library.view.ILibView;

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
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */

public class LibPresenterImp extends BasePresenterImp implements ILibPresenter {
    private static final String TAG = "LibPresenterImp";
    private ILibView mLibView;
    private Context mContext;
    private List<BookItem> mItems = new ArrayList<>();
    private int currentPos = -20;
    private int totalNums = 0;
    private int totalPages;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public LibPresenterImp(Context context, ILibView libView) {
        this.mContext = context;
        this.mLibView = libView;
        sharedPreferenceUtil=new SharedPreferenceUtil(mContext);
    }

    /**
     * 包括两种情况：
     * 1：用户第一次登录，登录成功后进入到LibFragment中，此时由于ServiceManager是单例，所以ServiceManager的实例已经存在，即
     * 已经成功登录到了图书馆，没有过期。所以此时进入到LibFragment时按理来说应该是直接调用getBorrowList()将数据
     * 拉下来。
     * 2: 用户以前登录过，账号密码存在本地，这次打开时应该是直接先调用doLogin()方法来登陆（因为以前登录的已经过期了），再调用调用getBorrowList()将数据
     * 拉下来。
     * <p>
     * 为了统一，这里我将第一种也归为第二种来处理。即：用户第一次登陆后，进入LibFragment还要再登陆一次，再拉数据下来。这样做方便移植到西大助手上面
     * 整体逻辑也比较清楚，不然加一些判断之类的搞得很烦~！！
     * <p>
     * 这样的话，在LibFragment中，只有进入到LibFragment中时调用getBorrowListByLogin()方法登录并拉数据下来。
     * 然后下拉刷新的时候可以调用getBorrowList()方法来直接拉数据，而不用登陆。因为当你操作刷新时，一定是前面的登录
     * 已经操作了的并且登录信息没有过期，所以可以直接拉数据。
     */

    @Override
    public void getBorrowListByLogin(final String userName, final String password) {
        //先登陆 并获取总数据数
        ServiceManager.getInstance().getLibService().getLoginParam(System.currentTimeMillis(), System.currentTimeMillis())
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        String lt = null;
                        String execution = null;
                        lt = s.substring(s.indexOf("['") + 2, s.indexOf("','"));
                        execution = s.substring(s.indexOf("','") + 3, s.lastIndexOf("']"));
                        Map<String, String> logindata = new HashMap<>();
                        logindata.put("referer", "http://202.202.121.3:99/gold/opac/mylibrary");
                        logindata.put("isajax", "true");
                        logindata.put("loginUlr", "http://202.202.121.3:8080/login");
                        logindata.put("isframe", "true");
                        logindata.put("lt", lt);
                        logindata.put("execution", execution);
                        logindata.put("_eventId", "submit");
                        logindata.put("sign", "0");
                        logindata.put("authType", "centerCas");
                        logindata.put("username", userName);
                        logindata.put("password", password);
                        return ServiceManager.getInstance().getLibService().loginLib(logindata);
                    }
                }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return ServiceManager.getInstance().getLibService().userLib();
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return ServiceManager.getInstance().getLibService().getBorrowHistory(0);
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Parser.getTotalPage(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted => getNum");

                        //再拿到总数据数时，开始获取所有数据
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i <= totalNums; i += 20) {
                            list.add(i);
                        }
                        getBook(list);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError()=> getNum" + throwable.toString());
                        mLibView.hideProgressDialog();
                        mLibView.showError(throwable.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext()=> getNum");
                        totalNums = integer;
                    }
                });


    }

    /**
     *
     * @param list  通过该参数中的值来获取所有数据。list中的数据应该是这样的： 0、20、40、60等
     */

    private void getBook(List<Integer> list) {
        Observable.from(list).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                Log.d(TAG, "flatMap call interger==>" + integer);
                return ServiceManager.getInstance().getLibService().getBorrowHistory(integer);
            }
        }).buffer(list.size())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "onError " + throwable.toString());
                        mLibView.updateData(sharedPreferenceUtil.getBookList());
                        mLibView.hideProgressDialog();
                        mLibView.showError("未知错误，已帮你加载缓存数据");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        List<BookItem> bookItems = new ArrayList<BookItem>();
                        for (String s : strings) {
                            Log.d(TAG, "\n\n\n#########################################################");
                            Log.d(TAG, s);
                            Log.d(TAG, "#########################################################\n\n\n");
                            bookItems.addAll(Parser.makeBookItems(s));
                        }
                        mLibView.updateData(bookItems);
                        sharedPreferenceUtil.putBookList(bookItems);
                    }
                });
    }

    /**
     *  用户下拉刷新时调用，此时不需要再次登录
     *
     */
    @Override
    public void getBorrowList() {
        mLibView.showRefreshLayout();
        ServiceManager.getInstance().getLibService().getBorrowHistory(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        totalNums = Parser.getTotalPage(s);
                        return totalNums;
                    }
                }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                mLibView.hideRefreshLayout();
                mLibView.showFab();
            }

            @Override
            public void onError(Throwable e) {
                mLibView.showErrorView();
                mLibView.showError("onError getBorrowList " + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                //这种做法很笨，但是我暂时没想出其他好办法
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i <= totalNums; i += 20) {
                    list.add(i);
                }
                getBook(list);
            }
        });
    }

}
