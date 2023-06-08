package com.example.tot_educational.Model;

import java.util.Comparator;

public class model
{
    String filename, fileurl,date,time;
    int nod,nol,nov;

    public model() {
    }

    public model(String filename, String fileurl, int nod, int nol, int nov,String date,String time) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.nod = nod;
        this.nol = nol;
        this.nov = nov;
        this.date = date;
        this.time = time;
    }
    public static Comparator<model> nameAtoZ=new Comparator<model>() {
        @Override
        public int compare(model p1, model p2) {

            return p1.getFilename().compareTo(p2.getFilename());
        }
    };

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public int getNod() {
        return nod;
    }

    public void setNod(int nod) {
        this.nod = nod;
    }

    public int getNol() {
        return nol;
    }

    public void setNol(int nol) {
        this.nol = nol;
    }

    public int getNov() {
        return nov;
    }

    public void setNov(int nov) {
        this.nov = nov;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
