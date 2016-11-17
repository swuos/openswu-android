package com.swuos.ALLFragment.library.library.presenter;

/**
 * Created by : youngkaaa on 2016/10/27.
 * Contact me : 645326280@qq.com
 */

public interface ILibPresenter extends IBasePresenter{
    void getBorrowListByLogin(String userName, String password);
    void getBorrowList();
}
