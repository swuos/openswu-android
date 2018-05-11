package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AllWeeksClass implements Parcelable{
    ArrayList<WeekClasses> weekClasses;

    public ArrayList<WeekClasses> getWeekClasses() {
        return weekClasses;
    }

    public void setWeekClasses(ArrayList<WeekClasses> weekClasses) {
        this.weekClasses = weekClasses;
    }

    public static Creator<AllWeeksClass> getCREATOR() {
        return CREATOR;
    }

    public AllWeeksClass(Parcel in) {
        weekClasses = in.createTypedArrayList(WeekClasses.CREATOR);
    }
    public AllWeeksClass()
    {}

    public static final Creator<AllWeeksClass> CREATOR = new Creator<AllWeeksClass>() {
        @Override
        public AllWeeksClass createFromParcel(Parcel in) {
            return new AllWeeksClass(in);
        }

        @Override
        public AllWeeksClass[] newArray(int size) {
            return new AllWeeksClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(weekClasses);
    }

    public static class WeekClasses implements Parcelable {
        int weekSort;
        ArrayList<ClassItemDetail> weekitem;

        public int getWeekSort() {
            return weekSort;
        }

        public void setWeekSort(int weekSort) {
            this.weekSort = weekSort;
        }

        public ArrayList<ClassItemDetail> getWeekitem() {
            return weekitem;
        }

        public void setWeekitem(ArrayList<ClassItemDetail> weekitem) {
            this.weekitem = weekitem;
        }

        protected WeekClasses(Parcel in) {
            weekSort = in.readInt();
            weekitem = in.createTypedArrayList(ClassItemDetail.CREATOR);
        }
public WeekClasses()
{}
        public static final Creator<WeekClasses> CREATOR = new Creator<WeekClasses>() {
            @Override
            public WeekClasses createFromParcel(Parcel in) {
                return new WeekClasses(in);
            }

            @Override
            public WeekClasses[] newArray(int size) {
                return new WeekClasses[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(weekSort);
            dest.writeTypedList(weekitem);
        }
    }

}
