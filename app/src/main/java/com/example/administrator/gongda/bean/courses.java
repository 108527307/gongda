package com.example.administrator.gongda.bean;

/**
 * Created by Administrator on 2016/9/19.
 */
public class courses {
     private String kechenmingchen;
    private String kechenhao;
    private String didian;
    private String jieshu;
    private String jiangshi;
    private String kechenzhou;

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getXinqiji() {
        return xinqiji;
    }

    public void setXinqiji(String xinqiji) {
        this.xinqiji = xinqiji;
    }

    private String xinqiji;
    private String xuehao;
    public courses(String jieshu, String kechenmingchen, String kechenhao, String didian, String jiangshi, String kechenzhou, String xiaoquming, String xuenian, String xueqi, String xinqi,String xuehao,String xinqiji) {
        this.jieshu = jieshu;
        this.kechenmingchen = kechenmingchen;
        this.kechenhao = kechenhao;
        this.didian = didian;
        this.jiangshi = jiangshi;
        this.kechenzhou = kechenzhou;
        this.xiaoquming = xiaoquming;
        this.xuenian = xuenian;
        this.xueqi = xueqi;
        this.xinqi = xinqi;
        this.xuehao=xuehao;
        this.xinqiji=xinqiji;
    }

    private String xiaoquming;
    private String xuenian;
    private String xueqi;
    private String xinqi;
public int getStartnum(){
    String[] temp=jieshu.split("-");
    return  Integer.parseInt(temp[0]);
}
    public int getEndnum(){
        String[] temp=jieshu.split("-");
        return Integer.parseInt(temp[1]);
    }
    public String getJiangshi() {
        return jiangshi;
    }

    public void setJiangshi(String jiangshi) {
        this.jiangshi = jiangshi;
    }

    public String getKechenmingchen() {
        return kechenmingchen;
    }

    public void setKechenmingchen(String kechenmingchen) {
        this.kechenmingchen = kechenmingchen;
    }

    public String getKechenhao() {
        return kechenhao;
    }

    public void setKechenhao(String kechenhao) {
        this.kechenhao = kechenhao;
    }

    public String getDidian() {
        return didian;
    }

    public void setDidian(String didian) {
        this.didian = didian;
    }

    public String getJieshu() {
        return jieshu;
    }

    public void setJieshu(String jieshu) {
        this.jieshu = jieshu;
    }

    public String getKechenzhou() {
        return kechenzhou;
    }

    public void setKechenzhou(String kechenzhou) {
        this.kechenzhou = kechenzhou;
    }

    public String getXiaoquming() {
        return xiaoquming;
    }

    public void setXiaoquming(String xiaoquming) {
        this.xiaoquming = xiaoquming;
    }

    public String getXuenian() {
        return xuenian;
    }

    public void setXuenian(String xuenian) {
        this.xuenian = xuenian;
    }

    public String getXueqi() {
        return xueqi;
    }

    public void setXueqi(String xueqi) {
        this.xueqi = xueqi;
    }

    public String getXinqi() {
        return xinqi;
    }

    public void setXinqi(String xinqi) {
        this.xinqi = xinqi;
    }
}
