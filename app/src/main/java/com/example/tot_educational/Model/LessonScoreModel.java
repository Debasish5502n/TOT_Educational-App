package com.example.tot_educational.Model;

import java.util.Comparator;

public class LessonScoreModel {
    String name,image,uid,correct_poll,wrong_poll,total_poll,select_poll;

    public LessonScoreModel() {
    }

    public LessonScoreModel(String name, String image, String uid, String correct_poll, String wrong_poll, String total_poll, String select_poll) {
        this.name = name;
        this.image = image;
        this.uid = uid;
        this.correct_poll = correct_poll;
        this.wrong_poll = wrong_poll;
        this.total_poll = total_poll;
        this.select_poll = select_poll;
    }

    public static Comparator<LessonScoreModel> score=new Comparator<LessonScoreModel>() {
        @Override
        public int compare(LessonScoreModel p1, LessonScoreModel p2) {

            return p1.getCorrect_poll().compareTo(p2.getCorrect_poll());
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCorrect_poll() {
        return correct_poll;
    }

    public void setCorrect_poll(String correct_poll) {
        this.correct_poll = correct_poll;
    }

    public String getWrong_poll() {
        return wrong_poll;
    }

    public void setWrong_poll(String wrong_poll) {
        this.wrong_poll = wrong_poll;
    }

    public String getTotal_poll() {
        return total_poll;
    }

    public void setTotal_poll(String total_poll) {
        this.total_poll = total_poll;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSelect_poll() {
        return select_poll;
    }

    public void setSelect_poll(String select_poll) {
        this.select_poll = select_poll;
    }

    public static Comparator<LessonScoreModel> getScore() {
        return score;
    }

    public static void setScore(Comparator<LessonScoreModel> score) {
        LessonScoreModel.score = score;
    }
}
