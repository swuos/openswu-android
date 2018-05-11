package com.swuos.mobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The type Class item detail.
 */
public class ClassItemDetail implements Parcelable {
    /**
     * "academicYear":2017,//课程所属学年
     * "term":1,//课程所属学期
     * "lessonId":"2016-1000010",//课程id
     * "lessonName":"数据结构",//课程名称
     * "teacher":"王平",//授课老师
     * "academicTitle":"副教授",//教师职称
     * "startTime":4,//课程开始节
     * "endTime":5,//课程结束节
     * "day":3,//课程所在星期
     * "week":"星期三",//课程所在星期，展示用
     * "campus":"北区",//课程所在南北区
     * "classRoom":"08-0505", //上课地点
     */
    private String academicYear;
    private String term;
    private String lessonId;
    private String lessonName;
    private String teacher;
    private String academicTitle;
    private int startTime;
    private int endTime;
    private int day;
    private String week;
    private String campus;
    private String classRoom;

    /**
     * Gets academic year.
     *
     * @return the academic year
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * Sets academic year.
     *
     * @param academicYear the academic year
     */
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * Gets term.
     *
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets term.
     *
     * @param term the term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Gets lesson id.
     *
     * @return the lesson id
     */
    public String getLessonId() {
        return lessonId;
    }

    /**
     * Sets lesson id.
     *
     * @param lessonId the lesson id
     */
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * Gets lesson name.
     *
     * @return the lesson name
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * Sets lesson name.
     *
     * @param lessonName the lesson name
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * Gets teacher.
     *
     * @return the teacher
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Sets teacher.
     *
     * @param teacher the teacher
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * Gets academic title.
     *
     * @return the academic title
     */
    public String getAcademicTitle() {
        return academicTitle;
    }

    /**
     * Sets academic title.
     *
     * @param academicTitle the academic title
     */
    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Gets week.
     *
     * @return the week
     */
    public String getWeek() {
        return week;
    }

    /**
     * Sets week.
     *
     * @param week the week
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * Gets campus.
     *
     * @return the campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * Sets campus.
     *
     * @param campus the campus
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * Gets class room.
     *
     * @return the class room
     */
    public String getClassRoom() {
        return classRoom;
    }

    /**
     * Sets class room.
     *
     * @param classRoom the class room
     */
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public static Creator<ClassItemDetail> getCREATOR() {
        return CREATOR;
    }

    /**
     * Instantiates a new Class item detail.
     *
     * @param in the in
     */
    protected ClassItemDetail(Parcel in) {
        academicYear = in.readString();
        term = in.readString();
        lessonId = in.readString();
        lessonName = in.readString();
        teacher = in.readString();
        academicTitle = in.readString();
        startTime = in.readInt();
        endTime = in.readInt();
        day = in.readInt();
        week = in.readString();
        campus = in.readString();
        classRoom = in.readString();
    }

    public ClassItemDetail()
    {
    }

    /**
     * The constant CREATOR.
     */
    public static final Creator<ClassItemDetail> CREATOR = new Creator<ClassItemDetail>() {
        @Override
        public ClassItemDetail createFromParcel(Parcel in) {
            return new ClassItemDetail(in);
        }

        @Override
        public ClassItemDetail[] newArray(int size) {
            return new ClassItemDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(academicYear);
        dest.writeString(term);
        dest.writeString(lessonId);
        dest.writeString(lessonName);
        dest.writeString(teacher);
        dest.writeString(academicTitle);
        dest.writeInt(startTime);
        dest.writeInt(endTime);
        dest.writeInt(day);
        dest.writeString(week);
        dest.writeString(campus);
        dest.writeString(classRoom);
    }
}
