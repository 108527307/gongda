package com.example.administrator.gongda.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.bean.gpis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 */
public class stuDatabaseDao {
    private String DATABASE_NAME="userinfo.db";

    private int DATABADE_VERSION=1;
    public StidataBaseHelper stidataBaseHelper;
    public stuDatabaseDao(Context context){
        this.stidataBaseHelper=new StidataBaseHelper(context,DATABASE_NAME,null,DATABADE_VERSION);
    }
    public void AddUserInfo(List<String> userinfo){
        Object[] temparray=new Object[6];
        int j=0;
        for(String i : userinfo){
            temparray[j++]=i;
        }
        SQLiteDatabase sqLiteDatabase=this.stidataBaseHelper.getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("insert into users values(?,?,?,?,?,?)", temparray);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }
    public void AddGpiInfo(List<gpis> gpises){
        SQLiteDatabase localSqLiteDatabase=this.stidataBaseHelper.getWritableDatabase();
        Object[] arrayObject=new Object[17];
        for(gpis gpi : gpises) {
            arrayObject[0] = gpi.getXuehao();
            arrayObject[1] = gpi.getBanji();
            arrayObject[2] = gpi.getChenji();
            arrayObject[3] = gpi.getJidian();
            arrayObject[4] = gpi.getXueyuan();
            arrayObject[5] = gpi.getXueqi();
            arrayObject[6] = gpi.getShijian();
            arrayObject[7] = gpi.getKechendaima();
            arrayObject[8] = gpi.getKechenmignchen();
            arrayObject[9] = gpi.getKcxzdm();
            arrayObject[10] = gpi.getKechenleixin();
            arrayObject[11] = gpi.getKaikexueyuan();
            arrayObject[12] = gpi.getKaoshixinzhi();
            arrayObject[13] = gpi.getXuenian();
            arrayObject[14] = gpi.getName();
            arrayObject[15] = gpi.getXinbie();
            arrayObject[16] = gpi.getZhuanye();

            localSqLiteDatabase.execSQL("insert into gpis values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",arrayObject);
        }
    }
    public void AddCourseInfo(List<courses> courseses){
        SQLiteDatabase localSqLiteDatabase=this.stidataBaseHelper.getWritableDatabase();
        Object[] arrayObject=new Object[12];
        for(courses gpi : courseses) {
            arrayObject[0] = gpi.getJieshu();
            arrayObject[1] = gpi.getKechenmingchen();
            arrayObject[2] = gpi.getKechenhao();
            arrayObject[3] = gpi.getDidian();
            arrayObject[4] = gpi.getJiangshi();
            arrayObject[5] = gpi.getKechenzhou();
            arrayObject[6] = gpi.getXiaoquming();
            arrayObject[7] = gpi.getXuenian();
            arrayObject[8] = gpi.getXueqi();
            arrayObject[9] = gpi.getXinqi();
            arrayObject[10]=gpi.getXuehao();
            arrayObject[11]=gpi.getXinqiji();
            localSqLiteDatabase.execSQL("insert into courses values(?,?,?,?,?,?,?,?,?,?,?,?)",arrayObject);
        }
    }
    public List<gpis> showAllGpa(){
        List<gpis> localList=new ArrayList<>();
        Cursor cursor=this.stidataBaseHelper.getWritableDatabase().rawQuery("select * from gpis  order by chenji DESC",new String[]{});
        while (cursor.moveToNext()){
            localList.add(new gpis(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16)));
        }
        cursor.close();
        return localList;
    }
    public List<gpis>  showAllgpisById(String xuenian,String xueqi){
        List<gpis> localList=new ArrayList<>();
        Cursor cursor=this.stidataBaseHelper.getWritableDatabase().rawQuery("select * from gpis where xuenian = ? and xueqi = ? order by chenji DESC",new String[]{xuenian,xueqi});
        while (cursor.moveToNext()){
            localList.add(new gpis(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16)));
        }
        cursor.close();
        return localList;
    }
    public List<courses>  showAllcouresesById(String xuenian,String xueqi){
        List<courses> localList=new ArrayList<>();
        Cursor cursor=this.stidataBaseHelper.getWritableDatabase().rawQuery("select * from courses where xuenian = ? and xueqi = ? ",new String[]{xuenian,xueqi});
        while (cursor.moveToNext()){
            localList.add(new courses(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11)));
        }
        cursor.close();
        return localList;
    }
    public List<courses> getCourseByWeekNum(int num,int year,int xueqi) throws Throwable {
        List<courses> courseslist=new ArrayList<>();
        Cursor cursor=this.stidataBaseHelper.getReadableDatabase().rawQuery("select * from courses where xuenian= ? and  xueqi = ?",new String[]{year+"",xueqi+""});
                while(cursor.moveToNext()){
                    String kechenzhounum=cursor.getString(5).trim();
//                    if(kechenzhounum.contains("双")||kechenzhounum.contains("单"))
//                        kechenzhounum=kechenzhounum.substring(0,kechenzhounum.length()-3);
                    if(kechenzhounum.contains(",")){
                        String[] str=kechenzhounum.split(",");
                        if(str[0].contains("双")||str[0].contains("单"))
                            str[0]=str[0].substring(0,str[0].length()-3);
                        if(str[1].contains("双")||str[1].contains("单"))
                            str[1]=str[1].substring(0,str[1].length()-3);

                       String[] st1=str[0].substring(0,str[0].length()-1).split("-");
                        String[] st2=str[1].substring(0,str[1].length()-1).split("-");
                        String[] st3=st2;

                        if(str.length==3){
                            if(str[2].contains("双")||str[2].contains("单"))
                                str[2]=str[2].substring(0,str[2].length()-3);
                           st3=str[2].substring(0,str[2].length()-1).split("-");
                        }
                        if((num>=Integer.parseInt(st1[0])&&num<=Integer.parseInt(st1[1]))||(num>=Integer.parseInt(st2[0])&&num<=Integer.parseInt(st2[1]))||(num>=Integer.parseInt(st3[0])&&num<=Integer.parseInt(st3[1]))){
                            courseslist.add(new courses(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11)));
                        }
                        st1=null;
                        st2=null;
                        str=null;
                    }else if(kechenzhounum.contains("-")){
                        if(kechenzhounum.contains("双")||kechenzhounum.contains("单"))
                            kechenzhounum=kechenzhounum.substring(0,kechenzhounum.length()-3);
                        String[] st1 = kechenzhounum.substring(0, kechenzhounum.length() - 1).split("-");
                        if (num >= Integer.parseInt(st1[0]) && num <= Integer.parseInt(st1[1])) {
                            courseslist.add(new courses(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11)));
                        }
                        st1=null;
                    }else{
                        if(num==Integer.parseInt(kechenzhounum.substring(0,kechenzhounum.length()-1))){
                            courseslist.add(new courses(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9), cursor.getString(10), cursor.getString(11) ));
                        }
                    }
                }
        cursor.close();
        if(courseslist.size()==0){
            courseslist.add(new courses("1-8","本周没课，出去(｡･∀･)ﾉﾞ嗨","asdas","!!!","c","c","校外","d","d","s","d","1"));
        }
        return courseslist;
    }
    public Map getUserInfo(){
        Map userlist=new HashMap();
        Cursor cursor =this.stidataBaseHelper.getReadableDatabase().rawQuery("select * from users",new String[]{});
        while(cursor.moveToNext()){
            String user_id=cursor.getString(0).trim();
            String name=cursor.getString(2).trim();
            userlist.put("sessionUserKey",user_id);
            userlist.put("psd",cursor.getString(1).trim());
            userlist.put("name",name);
            userlist.put("xueyuan",cursor.getString(3).trim());
            userlist.put("con","con");
            userlist.put("image",cursor.getString(5));
        }
        cursor.close();
        return userlist;
    }
    public boolean destroyTables(Context context){
       return  stidataBaseHelper.deleteDataBase(context);
    }
    public List<courses> getCoursesByQueryString(String sql){
        List<courses> localList=new ArrayList<>();
        Cursor cursor=this.stidataBaseHelper.getReadableDatabase().rawQuery(sql,new String[]{});
        while (cursor.moveToNext()){
            localList.add(new courses(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9), cursor.getString(10), cursor.getString(11) ));
        }
        cursor.close();
        return localList;
    }
    public void deleteCourseByStrings(List<String> list){
        this.stidataBaseHelper.getWritableDatabase().execSQL("delete from courses where jieshu='"+list.get(0)+"' and kechenmingchen='"+list.get(1)+"' and kechenzhou='"+list.get(2)+"' and xinqiji='"+list.get(3)+"'");
    }
}
