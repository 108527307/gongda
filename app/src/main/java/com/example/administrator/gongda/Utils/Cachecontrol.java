package com.example.administrator.gongda.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.gongda.dataBase.stuDatabaseDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Cachecontrol {
public String cacheImage(String user_id,Bitmap bitmap) throws IOException {
    File imageDir=new File(Environment.getExternalStorageDirectory().getPath()+"/NjtectImageList/");
    if (!imageDir.exists())
        imageDir.mkdir();
    File cache=new File(Environment.getExternalStorageDirectory().getPath()+"/NjtectImageList/","NjtechImage"+user_id+".png");
    if(!cache.exists()){
        cache.createNewFile();
    Log.i("imagePath",cache.getAbsolutePath());
    FileOutputStream fo=new FileOutputStream(cache);
    bitmap.compress(Bitmap.CompressFormat.PNG,100,fo);
    fo.flush();
    fo.close();
    }
    return cache.getAbsolutePath();
}
    public void deleteImage(String imageUrl){
        File temp=new File(imageUrl);
        if(temp.exists())
            temp.delete();
    }
public void cacheTime(int ruxueYear,int daji,int xueqi,int dangqianzhou,Context context){
    SharedPreferences sharedPreferences=context.getSharedPreferences("userXiaoinfo",Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=sharedPreferences.edit();
    int year=ruxueYear+2000;
    int chooseYear;
    if(xueqi==1){
        chooseYear=year+daji+1;
    }else{
        chooseYear=year+1+daji;
    }
      editor.putInt("ruxuenianfen",year);
    editor.putInt("chooseYear",chooseYear-1);
    editor.putInt("dangqianzhou",dangqianzhou+1);
    editor.putInt("xueqi",xueqi+1);
    editor.commit();
}
    public  void finishAllInfo(Context context){
        deleteImage((String) new stuDatabaseDao(context).getUserInfo().get("image"));
           boolean a=new stuDatabaseDao(context).destroyTables(context);
            SharedPreferences d1;
        d1=context.getSharedPreferences("appinfo",Context.MODE_PRIVATE);
        d1.edit().clear().commit();
        d1=context.getSharedPreferences("com.example.administrator.gongda_preferences",Context.MODE_PRIVATE);
        d1.edit().clear().commit();
        d1=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        d1.edit().clear().commit();d1=context.getSharedPreferences("userXiaoinfo",Context.MODE_PRIVATE);
        d1.edit().clear().commit();
        d1=context.getSharedPreferences("WebViewChromiumPrefs",Context.MODE_PRIVATE);
        d1.edit().clear().commit();


    }
    public void createWeek(Context context,String date,int term,int week){
        SharedPreferences sharedPreferences=context.getSharedPreferences("week",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String temp[]=date.split(";");
        editor.putString("firstYear",temp[0]);
        editor.putString("firstWeek",temp[1]);
        editor.putString("week",week+1+"");
        editor.putString("term",term+1+"");
        editor.putString("currentWeek",week+"");
        editor.commit();

    }
    public boolean UpdateWeek(Context context,String currentTime){
        SharedPreferences sharedPreferences=context.getSharedPreferences("week",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        int firstYear=Integer.valueOf(sharedPreferences.getString("firstYear","2014"));
        int firstWeek=Integer.valueOf(sharedPreferences.getString("firstWeek","1"));
        String temp[]=currentTime.split(";");
        int currentYear=Integer.valueOf(temp[0]);
        int currentWeek=Integer.valueOf(temp[1]);
        if(firstYear!=currentYear||(currentWeek-firstWeek)<0||(currentWeek-firstWeek)>20){
            return false;
        }
        editor.putString("currentWeek",(currentWeek-firstWeek-1+Integer.valueOf(sharedPreferences.getString("week","1")))+"");
        editor.commit();
        return true;
    }
    public int getWeek(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("week",Context.MODE_PRIVATE);
        String currentweek=sharedPreferences.getString("currentWeek","1");
        return Integer.valueOf(currentweek);
    }
}
