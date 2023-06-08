package com.example.tot_educational.Model;

public class SetsModel {
    String id;
    String sets_title,subject;
    int setsId;

    public SetsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSets_title() {
        return sets_title;
    }

    public void setSets_title(String sets_title) {
        this.sets_title = sets_title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSetsId() {
        return setsId;
    }

    public void setSetsId(int setsId) {
        this.setsId = setsId;
    }
}
