package com.example.tot_educational.Model;

public class BannerModel {
    int id;
    String video_url,pdf_url,banner_image,subscription;

    public BannerModel() {
    }

    public BannerModel(int id, String video_url, String pdf_url, String banner_image, String subscription) {
        this.id = id;
        this.video_url = video_url;
        this.pdf_url = pdf_url;
        this.banner_image = banner_image;
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
