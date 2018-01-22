package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.swuos.mobile.utils.json.JsonField;

/**
 * wifi信息
 * Created by wangyu on 2018/1/22.
 */

public class UserWifiInfo implements Parcelable {

    @JsonField("studentId")
    private String studentId;
    @JsonField("balance")
    private String balance;//余额？

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.balance);
    }

    public UserWifiInfo() {
    }

    protected UserWifiInfo(Parcel in) {
        this.studentId = in.readString();
        this.balance = in.readString();
    }

    public static final Parcelable.Creator<UserWifiInfo> CREATOR = new Parcelable.Creator<UserWifiInfo>() {
        @Override
        public UserWifiInfo createFromParcel(Parcel source) {
            return new UserWifiInfo(source);
        }

        @Override
        public UserWifiInfo[] newArray(int size) {
            return new UserWifiInfo[size];
        }
    };
}
