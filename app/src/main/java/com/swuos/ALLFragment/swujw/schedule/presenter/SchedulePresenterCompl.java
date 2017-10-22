package com.swuos.ALLFragment.swujw.schedule.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.swuos.ALLFragment.swujw.TotalInfos;
import com.swuos.ALLFragment.swujw.net.api.SwuJwApi;
import com.swuos.ALLFragment.swujw.net.jsona.LoginJson;
import com.swuos.ALLFragment.swujw.net.util.RSAUtil;
import com.swuos.ALLFragment.swujw.schedule.model.Schedule;
import com.swuos.ALLFragment.swujw.schedule.model.ScheduleDatas;
import com.swuos.ALLFragment.swujw.schedule.view.IScheduleView;
import com.swuos.swuassistant.Constant;
import com.swuos.util.SALog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 张孟尧 on 2016/8/3.
 */
public class SchedulePresenterCompl implements ISchedulePresenter {
    private Context mContext;
    private IScheduleView iScheduleView;
    private TotalInfos totalInfos;
    private SharedPreferences userInfosharedPreferences;
    private SharedPreferences settingSharedPreferences;

    private SharedPreferences.Editor editor;
    private String xnm;
    private String xqm;


    public SchedulePresenterCompl(Context mContext, IScheduleView iScheduleView) {
        this.mContext = mContext;
        this.iScheduleView = iScheduleView;
        this.totalInfos = TotalInfos.getInstance();
        userInfosharedPreferences = mContext.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        settingSharedPreferences = mContext.getSharedPreferences("com.swuos.swuassistant_preferences", MODE_PRIVATE);
        editor = userInfosharedPreferences.edit();
    }


    @Override
    public String getUsername() {
        return totalInfos.getUserName();
    }

    @Override
    public String getPassword() {
        return totalInfos.getPassword();
    }

    @Override
    public String getXnm() {
        return xnm;
    }

    @Override
    public void setXnm(String xnm) {
        this.xnm = xnm;
    }

    @Override
    public String getXqm() {
        return xqm;
    }

    @Override
    public void setXqm(String xqm) {
        this.xqm = xqm;
    }

    @Override
    public void initData() {
        setXnmAndXqm();
        if (!totalInfos.getSwuID().equals("")) {
            totalInfos.setScheduleDataJson(userInfosharedPreferences.getString("scheduleDataJson", ""));
           /*判断为课程表从未被获取,开始获取课程表*/
            if (totalInfos.getScheduleDataJson().equals("")) {
                getSchedule(totalInfos.getUserName(), totalInfos.getPassword(), xnm, xqm);
            } else if (totalInfos.getScheduleItemList().isEmpty()) {
                totalInfos.setScheduleItemList(Schedule.getScheduleList(totalInfos));
            }

        }
    }

    @Override
    public void getSchedule(final String username, final String password, final String xnm, final String xqm) {
        iScheduleView.showDialog(true);


        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return SwuJwApi.jwSchedule().getSchedule(totalInfos.getSwuID(), xnm, xqm);
            }
        }).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                if (s.contains("登录超时"))
                    return Observable.error(new Throwable("登录超时"));
                else {
                    totalInfos.setScheduleDataJson(s);
                    Gson gson = new Gson();
                    totalInfos.setScheduleData(gson.fromJson(totalInfos.getScheduleDataJson(), ScheduleDatas.class));
                    totalInfos.setScheduleItemList(Schedule.getScheduleList(totalInfos));
                        /*将获取的课程表json信息写入本地文件*/
                    editor.putString("scheduleDataJson", totalInfos.getScheduleDataJson());
                    editor.commit();
                    return Observable.just(true);
                }
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable.getMessage().contains("登录超时")) {
                            String swuLoginjsons = String.format("{\"serviceAddress\":\"https://uaaap.swu.edu.cn/cas/ws/acpInfoManagerWS\",\"serviceType\":\"soap\",\"serviceSource\":\"td\",\"paramDataFormat\":\"xml\",\"httpMethod\":\"POST\",\"soapInterface\":\"getUserInfoByUserName\",\"params\":{\"userName\":\"%s\",\"passwd\":\"%s\",\"clientId\":\"yzsfwmh\",\"clientSecret\":\"1qazz@WSX3edc$RFV\",\"url\":\"http://i.swu.edu.cn\"},\"cDataPath\":[],\"namespace\":\"\",\"xml_json\":\"\",\"businessServiceName\":\"uaaplogin\"}", username, password);
                            return SwuJwApi.loginIswu().login(RSAUtil.encrypt(swuLoginjsons)).flatMap(new Func1<LoginJson, Observable<?>>() {
                                @Override
                                public Observable<?> call(LoginJson loginJson) {
                                    String tgt = loginJson.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getAttributes().getTgt();
                                    String cookie = String.format("CASTGC=\"%s\"; rtx_rep=no", new String(Base64.decode(tgt, Base64.DEFAULT)));
                                    return SwuJwApi.loginJw(cookie).login();
                                }
                            });
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                               @Override
                               public void onCompleted() {
                                   iScheduleView.showDialog(false);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   SALog.d("SchedulePresenterCompl", "e:" + e.getMessage());
                                   String error = e.getMessage();
                                   iScheduleView.showDialog(false);
                                   if (e instanceof UnknownHostException) {
                                       error = Constant.CLIENT_ERROR;
                                   } else if (e instanceof SocketTimeoutException) {
                                       error = Constant.CLIENT_TIMEOUT;
                                   }
                                   iScheduleView.showError(error);
                               }

                               @Override
                               public void onNext(Object s) {
                                   iScheduleView.showDialog(false);
                                   iScheduleView.showResult();
                               }
                           }

                );
    }

    @Override
    public void setXnmAndXqm() {
        xnm = settingSharedPreferences.getString("set_study_year", "0");
        xqm = settingSharedPreferences.getString("select_date", "0");
        SharedPreferences.Editor setEditor = settingSharedPreferences.edit();
        if (!xnm.matches("[0-9]+")) {
            iScheduleView.showError("请输入正确的学年,如2016");
            return;
        }
        if (xnm.equals("0")) {
            Calendar calendar = Calendar.getInstance();
            xnm = String.valueOf(calendar.get(Calendar.YEAR));
            //如果是在一月份,则是第一学期,学年也要减一
            if (calendar.get(Calendar.MONTH) <= 0) {
                xqm = "3";
                xnm = String.valueOf(calendar.get(Calendar.YEAR) - 1);
            }
            setEditor.putString("set_study_year", xnm);

        }
        if (xqm.equals("0")) {
            Calendar calendar = Calendar.getInstance();
            //大于8月份则是第一学期
            if (calendar.get(Calendar.MONTH) >= 7)
                xqm = "3";
            else if (calendar.get(Calendar.MONTH) >= 1 && calendar.get(Calendar.MONTH) <= 6) {
                xqm = "12";//在二月到7月之间属于第二学期
                xnm = String.valueOf(calendar.get(Calendar.YEAR) - 1);
            }

            //如果是在一月份,则是第一学期,学年也要减一
            if (calendar.get(Calendar.MONTH) <= 0) {
                xqm = "3";
                xnm = String.valueOf(calendar.get(Calendar.YEAR) - 1);
            }
            setEditor.putString("set_study_year", xnm);
            setEditor.putString("select_date", xqm);

        }

        setEditor.apply();

    }
}
