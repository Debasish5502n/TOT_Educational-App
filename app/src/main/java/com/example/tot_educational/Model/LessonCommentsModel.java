package com.example.tot_educational.Model;

import java.util.Comparator;

public class LessonCommentsModel {
    String lessonCommentImage,lessonCommentName,lessonComments,lessonCommentId,user,timeStamp;

    public LessonCommentsModel() {
    }

    public LessonCommentsModel(String lessonCommentImage, String lessonCommentName, String lessonComments, String lessonCommentId, String user, String timeStamp) {
        this.lessonCommentImage = lessonCommentImage;
        this.lessonCommentName = lessonCommentName;
        this.lessonComments = lessonComments;
        this.lessonCommentId = lessonCommentId;
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public static Comparator<LessonCommentsModel> latestChat=new Comparator<LessonCommentsModel>() {
        @Override
        public int compare(LessonCommentsModel p1, LessonCommentsModel p2) {

            return p1.getTimeStamp().compareTo(p2.getTimeStamp());
        }
    };


    public String getLessonCommentImage() {
        return lessonCommentImage;
    }

    public void setLessonCommentImage(String lessonCommentImage) {
        this.lessonCommentImage = lessonCommentImage;
    }

    public String getLessonCommentName() {
        return lessonCommentName;
    }

    public void setLessonCommentName(String lessonCommentName) {
        this.lessonCommentName = lessonCommentName;
    }

    public String getLessonComments() {
        return lessonComments;
    }

    public void setLessonComments(String lessonComments) {
        this.lessonComments = lessonComments;
    }

    public String getLessonCommentId() {
        return lessonCommentId;
    }

    public void setLessonCommentId(String lessonCommentId) {
        this.lessonCommentId = lessonCommentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
