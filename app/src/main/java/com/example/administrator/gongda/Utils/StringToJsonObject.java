package com.example.administrator.gongda.Utils;

import android.content.Context;

import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.bean.gpis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class StringToJsonObject {
    public List<gpis> parGpisList(String str){
        List<gpis> gpises=new ArrayList<>();
        try {
            JSONObject a=new JSONObject(str);
           JSONArray items= a.getJSONArray("items");


           for(int i=0;i<items.length();i++){
               JSONObject temp= (JSONObject) items.get(i);
               gpises.add(new gpis(temp.getString("xh"),(String)temp.get("bj"),(String)temp.get("cj"),(String)temp.get("jd"),(String)temp.get("jgmc"),(String)temp.get("xqmmc"),(String)temp.get("xf"),(String)temp.get("jxb_id"),(String)temp.get("kcmc"),(String)temp.get("kcxzdm"),(String)temp.get("kcxzmc"),(String)temp.get("kkbmmc"),(String)temp.get("ksxz"),(String)temp.get("xnm"),(String)temp.get("xm"),(String)temp.get("xb"),(String)temp.get("zymc")));

           }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return gpises;
        }
    }
    public List<courses> parcoursesList(String str,Context context){
        List<courses> gpises=new ArrayList<>();
        try {
            JSONObject a=new JSONObject(str);
            JSONArray items= a.getJSONArray("kbList");
            JSONObject b=a.getJSONObject("xsxx");
//            SharedPreferences sp=context.getSharedPreferences("userXiaoinfo",Context.MODE_PRIVATE);
//            String xueqi=sp.getString("xueqi","1");



            for(int i=0;i<items.length();i++){
                JSONObject temp= (JSONObject) items.get(i);
                gpises.add(new courses(temp.getString("jcs"),(String)temp.get("kcmc"),(String)temp.get("kch_id"),(String)temp.get("cdmc"),(String)temp.get("xm"),(String)temp.get("zcd"),(String)temp.get("xqmc"),(String)temp.get("xnm"),temp.getString("xqm").equals("12")?"2":"1",(String)temp.get("xqjmc"),(String)b.get("XH_ID"),(String)temp.get("xqj")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return gpises;
        }
    }
}
