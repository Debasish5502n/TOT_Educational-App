package com.example.tot_educational.Model;

import java.util.Comparator;

public class User {
    private String uid;
    private String name;
    private String phoneNumber;
    private String profileImage;
    private String email;
    private String token;
    private String courseTitle;

    public User() {

    }
    public User(String uid, String name, String phoneNumber, String profileImage ,String email,String courseTitle) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.email=email;
        this.courseTitle = courseTitle;
    }

    public static Comparator<User> nameAtoZ=new Comparator<User>() {
        @Override
        public int compare(User p1, User p2) {

            return p1.getName().compareTo(p2.getName());
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
