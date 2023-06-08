package com.example.tot_educational.Model;

public class CategoryCourseModel {

    String categoryTitle,CategoryCourse;

    public CategoryCourseModel() {
    }

    public CategoryCourseModel(String categoryTitle, String getCategoryCourse) {
        this.categoryTitle = categoryTitle;
        this.CategoryCourse = getCategoryCourse;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryCourse() {
        return CategoryCourse;
    }

    public void setCategoryCourse(String getCategoryCourse) {
        this.CategoryCourse = getCategoryCourse;
    }
}
