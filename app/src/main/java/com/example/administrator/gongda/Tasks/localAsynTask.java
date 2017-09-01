package com.example.administrator.gongda.Tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.administrator.gongda.NetUtils.HttpUrls;
import com.example.administrator.gongda.NetUtils.Httputil;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.Utils.HtmlUtil;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.JsonCallBack;
import com.example.administrator.gongda.ui.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/16.
 */
public class localAsynTask extends AsyncTask<String,String,String> {
    Context context;
    JsonCallBack jsonCallBack;
    String title;
    String flag="failed";
    private stuDatabaseDao databaseDao;
    public localAsynTask(JsonCallBack jsonCallBack,String paramString,Context context){
        this.context=context;
        this.title=paramString;
        this.jsonCallBack=jsonCallBack;
        databaseDao=new stuDatabaseDao(context);
    }
    @Override
    protected String doInBackground(String[] params) {
        if(params[0].equals("no")){
            Map user=new HashMap();
            user=new stuDatabaseDao(context).getUserInfo();
            String[] s=new String[5];
            s[0]= HttpUrls.loginCheckurl;
            s[1]="yhm";
            s[2]= (String) user.get("sessionUserKey");
            s[3]="mm";
            s[4]= (String) user.get("psd");
            params=s;
        }
        String str=params[0];//url
        HashMap localHashMap=new HashMap();
        for(int i=1;i<params.length-1;i++)
            localHashMap.put(params[i],params[i+1]);
            Object[]  list= LoginActivity.httputil.HttpPost(str,localHashMap,context);
        try {
            if((new JSONObject(list[0].toString())).getString("status").equals("success")){
                flag="ok";
                String[] s=new String[5];
                s[0]= HttpUrls.loginurl;
                s[1]="yhm";
                s[2]=params[2];
                s[3]="mm";
                s[4]=params[4];
                HashMap localHashMap1=new HashMap();
                for(int i=1;i<params.length-1;i++)
                    localHashMap1.put(params[i],params[i+1]);
               LoginActivity.httputil.Login(s[0], localHashMap1,context);
            Bitmap userimage= (Bitmap) LoginActivity.httputil.HttpPostForImage(params[2],context);
                List<String> userinfo=new ArrayList<>();
                userinfo.add(s[2].toString());
                userinfo.add(s[4]);
                Map t=new HashMap();
                t.put("sessionUserKey",s[2]);
                try {
                    List<String> userInfo1=new ArrayList<>();
                           userInfo1= new HtmlUtil().getHtmlObject(Httputil.getContentWithPost(t,1,context),true);
                    userinfo.add(userInfo1.get(0));
                    userinfo.add(userInfo1.get(1));
                    userinfo.add("con");

                    userinfo.add(new Cachecontrol().cacheImage(s[2], userimage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                databaseDao.AddUserInfo(userinfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return flag;
    }

    @Override
    protected void onPostExecute(String s) {
        this.jsonCallBack.JsonLoaded(s);
        super.onPostExecute(s);
    }
}
