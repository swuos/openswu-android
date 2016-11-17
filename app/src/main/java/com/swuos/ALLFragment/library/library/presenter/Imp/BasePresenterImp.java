package com.swuos.ALLFragment.library.library.presenter.Imp;

import com.swuos.ALLFragment.library.library.presenter.IBasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by : youngkaaa on 2016/10/25.
 * Contact me : 645326280@qq.com
 */

public class BasePresenterImp implements IBasePresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription subscription){
        if(mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if(mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
