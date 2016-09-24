package com.swuos.ALLFragment.charge.view;

import com.mran.polylinechart.ChargeBean;

import java.util.List;

/**
 * Created by 张孟尧 on 2016/9/18.
 */

public interface IChargeFragment {
    void showBalance(String balance);
    void showDailyconsume(List<ChargeBean> chargeBeanList);
    void showError(String s);
}
