package com.example.administrator.gongda.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */

public class workDetailBean implements Parcelable{
    private String title;
    private String time;
    private String play;

    public workDetailBean(List<String> cla, String title, String time, String play, List<String> actor, String director, String location, String language, String atime, String imgurl, String info, List<Map<String, String>> playlist) {
        this.cla = cla;
        this.title = title;
        this.time = time;
        this.play = play;
        this.actor = actor;
        this.director = director;
        this.location = location;
        this.language = language;
        this.atime = atime;
        this.imgurl = imgurl;
        this.info = info;
        this.playlist = playlist;
    }

    private List<String> actor;

    protected workDetailBean(Parcel in) {
        title = in.readString();
        time = in.readString();
        play = in.readString();
        actor = in.createStringArrayList();
        director = in.readString();
        cla = in.createStringArrayList();
        location = in.readString();
        language = in.readString();
        atime = in.readString();
        imgurl = in.readString();
        info = in.readString();
    }

    public static final Creator<workDetailBean> CREATOR = new Creator<workDetailBean>() {
        @Override
        public workDetailBean createFromParcel(Parcel in) {
            return new workDetailBean(in);
        }

        @Override
        public workDetailBean[] newArray(int size) {
            return new workDetailBean[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Map<String, String>> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Map<String, String>> playlist) {
        this.playlist = playlist;
    }

    private String director;
    private List<String> cla;
    private String location;
    private String language;
    private String atime;
    private String imgurl;
    private String info;
    private List<Map<String,String>> playlist;

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
        dest.writeString(language);
        dest.writeString(atime);
        dest.writeString(imgurl);
        dest.writeString(info);
    }
}
