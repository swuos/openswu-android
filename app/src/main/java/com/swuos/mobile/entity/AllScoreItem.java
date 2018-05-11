package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AllScoreItem  implements Parcelable, Cloneable {
    private ArrayList<ScoreItem> arrayList;

    public AllScoreItem() {

    }

    protected AllScoreItem(Parcel in) {
        arrayList = in.createTypedArrayList(ScoreItem.CREATOR);
    }

    public static final Creator<AllScoreItem> CREATOR = new Creator<AllScoreItem>() {
        @Override
        public AllScoreItem createFromParcel(Parcel in) {
            return new AllScoreItem(in);
        }

        @Override
        public AllScoreItem[] newArray(int size) {
            return new AllScoreItem[size];
        }
    };

    public ArrayList<ScoreItem> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ScoreItem> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(arrayList);
    }
}
