package com.swuos.mobile.entity;

import com.gallops.mobile.jmvclibrary.utils.json.JsonField;

import java.util.List;

/**
 * {
 * "weekSort":1,
 * "weekItem":[
 * {
 * "academicYear":2013,
 * "term":1,
 * "lessonId":"21323280",
 * "lessonName":"名家讲台",
 * "teacher":"Sophie,任柯",
 * "academicTitle":"名家讲台",
 * "startTime":2,
 * "endTime":5,
 * "day":1,
 * "week":"星期一",
 * "campus":"北区",
 * "classRoom":"未排地点"
 * }
 * ]
 * }
 * Created by wangyu on 2018/5/12.
 */

public class WeekClasses {
    @JsonField("weekSort")
    private int weekSort;
    @JsonField("weekitem")
    private List<WeekItem> weekItem;

    public int getWeekSort() {
        return weekSort;
    }

    public void setWeekSort(int weekSort) {
        this.weekSort = weekSort;
    }

    public List<WeekItem> getWeekItem() {
        return weekItem;
    }

    public void setWeekItem(List<WeekItem> weekItem) {
        this.weekItem = weekItem;
    }

    public static class WeekItem {
        @JsonField("academicYear")
        private int academicYear;
        @JsonField("teacher")
        private String teacher;
        @JsonField("week")
        private String week;
        @JsonField("campus")
        private String campus;
        @JsonField("classRoom")
        private String classRoom;
        @JsonField("lessonId")
        private String lessonId;
        @JsonField("term")
        private int term;
        @JsonField("startTime")
        private int startTime;
        @JsonField("endTime")
        private int endTime;
        @JsonField("day")
        private int day;
        @JsonField("academicTitle")
        private String academicTitle;
        @JsonField("lessonName")
        private String lessonName;

        public int getAcademicYear() {
            return academicYear;
        }

        public void setAcademicYear(int academicYear) {
            this.academicYear = academicYear;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getCampus() {
            return campus;
        }

        public void setCampus(String campus) {
            this.campus = campus;
        }

        public String getClassRoom() {
            return classRoom;
        }

        public void setClassRoom(String classRoom) {
            this.classRoom = classRoom;
        }

        public String getLessonId() {
            return lessonId;
        }

        public void setLessonId(String lessonId) {
            this.lessonId = lessonId;
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getAcademicTitle() {
            return academicTitle;
        }

        public void setAcademicTitle(String academicTitle) {
            this.academicTitle = academicTitle;
        }

        public String getLessonName() {
            return lessonName;
        }

        public void setLessonName(String lessonName) {
            this.lessonName = lessonName;
        }

    }
}
