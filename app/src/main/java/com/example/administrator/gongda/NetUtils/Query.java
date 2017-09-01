package com.example.administrator.gongda.NetUtils;

import android.content.Context;

import com.example.administrator.gongda.Utils.StringToJsonObject;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.bean.gpis;
import com.example.administrator.gongda.ui.activity.LoginActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class Query {
    public static void queryAllGpis(String user_id,Context context){
        HashMap<String,String> tempMap=new HashMap<>();
        tempMap.put("sessionUserKey",user_id);
        tempMap.put("queryModel.showCount","100");
        tempMap.put("doType","query");
        tempMap.put("query","gnmkdmKey");
        tempMap.put("N305005","sessionUserKey");
        tempMap.put("gnmkdmKey","N305005");
        tempMap.put(user_id,"queryModel.showCount");
        List<gpis> list1= new StringToJsonObject().parGpisList(LoginActivity.httputil.getData(tempMap,HttpUrls.gradesurl,context));
        new stuDatabaseDao(context).AddGpiInfo(list1);
    }
    public static void queryCoursesByNumber(String user_id,String xnm,String xqm,Context context){
        HashMap<String,String> tempMap2=new HashMap<>();
        tempMap2.put("sessionUserKey",user_id);
        tempMap2.put("xqm",xqm);
        tempMap2.put("xnm",xnm);
        tempMap2.put("gnmkdmKey","N253508");
        tempMap2.put("N2151","sessionUserKey");
        List<courses> list2= new StringToJsonObject().parcoursesList(LoginActivity.httputil.getData(tempMap2, HttpUrls.courseurl, context),context);

        new stuDatabaseDao(context).AddCourseInfo(list2);
    }
}
