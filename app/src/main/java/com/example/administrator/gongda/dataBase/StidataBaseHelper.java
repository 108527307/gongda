package com.example.administrator.gongda.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/19.
 */
public class StidataBaseHelper extends SQLiteOpenHelper{


    public StidataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
  String sqlgpi="create table gpis ( xuehao text, banji  text , chenji text , jidian text , xueyuan text , xueqi text , shijian text , kechendaima text  , kechenmingchen text , kcxzdm text , kechenleixin text , kaikexueyuan text , kaoshixinzhi text , xuenian text , name text , xinbie text , zhuanye text);";
   String sqlcourse="create table courses( jieshu text , kechenmingchen text , kechenhao text  , didian text , jiangshi text , kechenzhou text , xiaoquming text , xuenian text , xueqi text , xinqi text , xuehao text , xinqiji text);";
    String sqlUser="create table users(username text  primary key not null , psd text , name text , xueyuan text , con text , image text)";
        db.execSQL(sqlUser);
     db.execSQL(sqlcourse);
        db.execSQL(sqlgpi);
    }
public boolean deleteDataBase(Context context){
    return context.deleteDatabase("userinfo.db");
}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
