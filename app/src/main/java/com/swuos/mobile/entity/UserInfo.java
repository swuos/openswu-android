package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.swuos.mobile.utils.json.JsonField;

/**
 * 测试用户信息
 * Created by wangyu on 2018/1/20.
 */

public class UserInfo implements Parcelable, Cloneable {
    @JsonField("")
    private String studentId;
    @JsonField("")
    private String studentName;
    private String token;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public UserInfo clone() {
        try {
            return (UserInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.studentName);
        dest.writeString(this.token);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.studentId = in.readString();
        this.studentName = in.readString();
        this.token = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
