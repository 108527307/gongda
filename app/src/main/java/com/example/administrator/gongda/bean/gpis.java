package com.example.administrator.gongda.bean;

/**
 * Created by Administrator on 2016/9/16.
 */
public class gpis {
   private String  xuehao;//学号
    private String banji;//班级
    private String chenji;//成绩
    private String jidian;//绩点
    private String xueyuan;//学院
    private String xueqi;//第几学期

    public gpis(String xuehao, String banji, String chenji, String jidian, String xueyuan, String xueqi, String shijian, String kechendaima, String kechenmignchen, String kcxzdm, String kechenleixin, String kaikexueyuan, String kaoshixinzhi, String xuenian, String name, String xinbie, String zhuanye) {
        this.xuehao = xuehao;
        this.banji = banji;
        this.chenji = chenji;
        this.jidian = jidian;
        this.xueyuan = xueyuan;
        this.xueqi = xueqi;
        this.shijian = shijian;
        this.kechendaima = kechendaima;
        this.kechenmignchen = kechenmignchen;
        this.kcxzdm = kcxzdm;
        this.kechenleixin = kechenleixin;
        this.kaikexueyuan = kaikexueyuan;
        this.kaoshixinzhi = kaoshixinzhi;
        this.xuenian = xuenian;
        this.name = name;
        this.xinbie = xinbie;
        this.zhuanye = zhuanye;
    }

    private String shijian;//录入时间

    public String getKechendaima() {
        return kechendaima;
    }

    public void setKechendaima(String kechendaima) {
        this.kechendaima = kechendaima;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getChenji() {
        return chenji;
    }

    public void setChenji(String chenji) {
        this.chenji = chenji;
    }

    public String getJidian() {
        return jidian;
    }

    public void setJidian(String jidian) {
        this.jidian = jidian;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public String getXueqi() {
        return xueqi;
    }

    public void setXueqi(String xueqi) {
        this.xueqi = xueqi;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getKechenmignchen() {
        return kechenmignchen;
    }

    public void setKechenmignchen(String kechenmignchen) {
        this.kechenmignchen = kechenmignchen;
    }

    public String getKcxzdm() {
        return kcxzdm;
    }

    public void setKcxzdm(String kcxzdm) {
        this.kcxzdm = kcxzdm;
    }

    public String getKechenleixin() {
        return kechenleixin;
    }

    public void setKechenleixin(String kechenleixin) {
        this.kechenleixin = kechenleixin;
    }

    public String getKaikexueyuan() {
        return kaikexueyuan;
    }

    public void setKaikexueyuan(String kaikexueyuan) {
        this.kaikexueyuan = kaikexueyuan;
    }

    public String getKaoshixinzhi() {
        return kaoshixinzhi;
    }

    public void setKaoshixinzhi(String kaoshixinzhi) {
        this.kaoshixinzhi = kaoshixinzhi;
    }

    public String getXuenian() {
        return xuenian;
    }

    public void setXuenian(String xuenian) {
        this.xuenian = xuenian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXinbie() {
        return xinbie;
    }

    public void setXinbie(String xinbie) {
        this.xinbie = xinbie;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye;
    }

    private String kechendaima;//课程代码
    private String kechenmignchen;//课程名称
    private  String kcxzdm;//不造
    private String kechenleixin;//课程类型
    private String kaikexueyuan;//开课学院
    private String kaoshixinzhi;//考试性质
    private String xuenian;//学年
    private String name;//姓名
    private String xinbie;//性别
    private String zhuanye;//专业
}
