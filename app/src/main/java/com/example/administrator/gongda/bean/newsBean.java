package com.example.administrator.gongda.bean;

/**
 * Created by Administrator on 2016/9/25.
 */

public class newsBean {
    private String title;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    private String time;
    private String url;

    public newsBean(String title, String time, String url, int ranking) {
        this.title = title;
        this.time = time;
        this.url = url;
        this.ranking = ranking;
    }

    private int ranking;

}
