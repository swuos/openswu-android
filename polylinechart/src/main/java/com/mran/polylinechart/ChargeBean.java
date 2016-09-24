package com.mran.polylinechart;

/**
 * Created by 张孟尧 on 2016/9/17.
 */

public class ChargeBean {
    private String date;
    private float coast;
    private String Balance;

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCoast() {
        return coast;
    }

    public void setCoast(float coast) {
        this.coast = coast;
    }
}
