package com.runtoinfo.youxiao.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

@SuppressWarnings("all")
public class CourseEntity {

    public String courseName;
    public String courseTime;
    public Drawable bitmap;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Drawable getBitmap() {
        return bitmap;
    }

    public void setBitmap(Drawable bitmap) {
        this.bitmap = bitmap;
    }
}
