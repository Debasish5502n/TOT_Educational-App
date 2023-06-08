package com.example.tot_educational.Model;

public class filemodel
{
  String title,vurl,purl;

    public filemodel() {
    }

    public filemodel(String title, String vurl,String purl) {
        this.title = title;
        this.vurl = vurl;
        this.purl =purl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
