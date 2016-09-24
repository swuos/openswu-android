package com.swuos.ALLFragment.charge.presenter;

import android.content.Context;

import com.mran.polylinechart.ChargeBean;
import com.swuos.ALLFragment.charge.model.ChargeApi;
import com.swuos.ALLFragment.charge.model.ChargeParse;
import com.swuos.ALLFragment.charge.view.IChargeFragment;
import com.swuos.util.SALog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张孟尧 on 2016/9/18.
 */

public class IChagerPresenterCompl implements IChargePresenetr {
    private Context mContext;
    private IChargeFragment iChargeFragment;
    private Subscriber subscriber;

    public IChagerPresenterCompl(Context mContext, IChargeFragment iChargeFragment) {
        this.mContext = mContext;
        this.iChargeFragment = iChargeFragment;
    }

    @Override
    public void query(String building, String room) {
        subscriber = new Subscriber<List<List<ChargeBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                SALog.d("IChagerPresenterCompl", throwable.getMessage());
                iChargeFragment.showError(throwable.getMessage());
            }

            @Override
            public void onNext(List<List<ChargeBean>> lists) {
                List<ChargeBean> chargeBeanList = new ArrayList<ChargeBean>();
                for (List<ChargeBean> a : lists) {
                    chargeBeanList.addAll(a);
                }
                iChargeFragment.showBalance(chargeBeanList.get(0).getBalance());
                iChargeFragment.showDailyconsume(chargeBeanList);
            }
        };
        ChargeApi.getCharge().chrgeLogin(building, "0" + room, "X" + room).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                if (!s.contains("目前剩余金额"))
                    return Observable.error(new Throwable("未查询到,请检查房间号是否存在"));
                return Observable.just(s);
            }
        }).flatMap(new Func1<Object, Observable<List<List<ChargeBean>>>>() {
            @Override
            public Observable<List<List<ChargeBean>>> call(Object o) {
                return Observable.range(1, 3).concatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return ChargeApi.getChargeBalance().chargeBalance(integer);
                    }
                }).map(new Func1<String, List<ChargeBean>>() {
                    @Override
                    public List<ChargeBean> call(String s) {
                        return ChargeParse.getChargeBean(s);
                    }
                }).buffer(3);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);


    }

    @Override
    public void cancle() {
        if (!subscriber.isUnsubscribed())
            subscriber.unsubscribe();
    }
}
