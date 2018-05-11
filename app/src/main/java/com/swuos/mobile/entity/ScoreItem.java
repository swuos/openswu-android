package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreItem implements Parcelable, Cloneable {
   private String score;
   private String lessonName;
   private String academicYear;
   private String term;
   private String gradePoint;
   private String credit;
   private String examType;
   private String lessonType;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public static Creator<ScoreItem> getCREATOR() {
        return CREATOR;
    }

    public ScoreItem() {
    }

    protected ScoreItem(Parcel in) {
        score = in.readString();
        lessonName = in.readString();
        academicYear = in.readString();
        term = in.readString();
        gradePoint = in.readString();
        credit = in.readString();
        examType = in.readString();
        lessonType = in.readString();
    }

    public static final Creator<ScoreItem> CREATOR = new Creator<ScoreItem>() {
        @Override
        public ScoreItem createFromParcel(Parcel in) {
            return new ScoreItem(in);
        }

        @Override
        public ScoreItem[] newArray(int size) {
            return new ScoreItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(score);
        dest.writeString(lessonName);
        dest.writeString(academicYear);
        dest.writeString(term);
        dest.writeString(gradePoint);
        dest.writeString(credit);
        dest.writeString(examType);
        dest.writeString(lessonType);
    }
}
