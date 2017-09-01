package com.example.administrator.gongda.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class workBean implements Parcelable {
    private String title;
    private String time;
    private String play;
    private List<String> actor;
    private String director;
    private List<String> cla;
    private String location;
    private String atime;
    private String imgurl;
    private String linkurl;
    public workBean(String title, String time, String play, List<String> actor, String director, List<String> cla, String location, String atime,String imgurl, String linkurl) {
        this.title = title;
        this.time = time;
        this.play = play;
        this.actor = actor;
        this.director = director;
        this.cla = cla;
        this.location = location;
        this.atime = atime;
        this.imgurl=imgurl;
        this.linkurl = linkurl;
    }

    protected workBean(Parcel in) {
        title = in.readString();
        time = in.readString();
        play = in.readString();
        actor = in.createStringArrayList();
        director = in.readString();
        cla = in.createStringArrayList();
        location = in.readString();
        atime = in.readString();
        imgurl=in.readString();
        linkurl = in.readString();
    }

    public static final Creator<workBean> CREATOR = new Creator<workBean>() {
        @Override
        public workBean createFromParcel(Parcel in) {
            return new workBean(in);
        }

        @Override
        public workBean[] newArray(int size) {
            return new workBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCla() {
        return cla;
    }

    public void setCla(List<String> cla) {
        this.cla = cla;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(play);
        dest.writeStringList(actor);
        dest.writeString(director);
        dest.writeStringList(cla);
        dest.writeString(location);
        dest.writeString(atime);
        dest.writeString(imgurl);
        dest.writeString(linkurl);
    }
}
