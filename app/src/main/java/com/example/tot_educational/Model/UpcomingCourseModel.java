package com.example.tot_educational.Model;

public class UpcomingCourseModel {

    String courseImage,courseCount,courseTitle,ed_name,ed_image,courseUpdate,ed_id,courseId,previewVideo,language,courseDetails,subject;
    Long courseStartDate,courseEndDate;

    public UpcomingCourseModel() {
    }

    public UpcomingCourseModel(String courseImage, String courseCount, String courseTitle, String ed_name, String ed_image, String courseDate ,String courseUpdate) {
        this.courseImage = courseImage;
        this.courseCount = courseCount;
        this.courseTitle = courseTitle;
        this.ed_name = ed_name;
        this.ed_image = ed_image;
        this.courseUpdate = courseUpdate;
    }

    public String getCourseUpdate() {
        return courseUpdate;
    }

    public void setCourseUpdate(String courseUpdate) {
        this.courseUpdate = courseUpdate;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getEd_name() {
        return ed_name;
    }

    public void setEd_name(String ed_name) {
        this.ed_name = ed_name;
    }

    public String getEd_image() {
        return ed_image;
    }

    public void setEd_image(String ed_image) {
        this.ed_image = ed_image;
    }

    public String getEd_id() {
        return ed_id;
    }

    public void setEd_id(String ed_id) {
        this.ed_id = ed_id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Long getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Long courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Long getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Long courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getPreviewVideo() {
        return previewVideo;
    }

    public void setPreviewVideo(String previewVideo) {
        this.previewVideo = previewVideo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
