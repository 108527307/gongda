package com.example.administrator.gongda.NetUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/16.
 */
public class Httputil {
    static final DefaultHttpClient defaulthttpclient=new DefaultHttpClient();
    static final DefaultHttpClient tempDefaultHttpClient=new DefaultHttpClient();
    static final DefaultHttpClient tempDefaultHttpClient1=new DefaultHttpClient();
    private CookieStore trans;
    private CookieStore trans2;


    public Object[] HttpPost(String paramString, Map paramMap, Context context){
        String str="";
        List<Cookie> locallist = null;
        HttpPost localHttpPost=new HttpPost(paramString);//设置httppost方法
        SharedPreferences sharedPreferences=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList  localArrayList=new ArrayList();
        if(paramMap!=null){
            Iterator iterator=paramMap.entrySet().iterator();//获取迭代器
            while(iterator.hasNext()){//使用迭代器将参数map类型转为arraylist类型
                Map.Entry localEntry=(Map.Entry)iterator.next();
                localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(),(String)localEntry.getValue()));
            }
        }
        try {
            if(sharedPreferences.getString("JSESSIONID",null)!=null){
                localHttpPost.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null)+";QINGCLOUDELB="+sharedPreferences.getString("QINGCLOUDELB",null));
            }
            localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
            HttpResponse localHttpResponse=defaulthttpclient.execute(localHttpPost);
            if(sharedPreferences.getString("JSESSIONID",null)==null) {
                CookieStore localCookieStore = defaulthttpclient.getCookieStore();
                trans = localCookieStore;
                locallist = localCookieStore.getCookies();
                for (Cookie i : locallist) {
                    editor.putString(i.getName(), i.getValue());
                    Log.i("cookie", i.toString());
                }
                editor.commit();
            }
            if(localHttpResponse.getStatusLine().getStatusCode()==200){
                str= EntityUtils.toString(localHttpResponse.getEntity(),"UTF-8");//将回应主体内容转化为utf-8格式的字符串
                Log.i("entity",str);
                //defaulthttpclient.getConnectionManager().shutdown();
            }else{
                Log.i("responseError",localHttpResponse.getStatusLine().getStatusCode()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
         Object[] o=new Object[2];
        o[0]=str;
        o[1]=locallist;
              return o;
    }
    public String getData(Map tempMap,String url,Context context){
     HttpPost httpPost=new HttpPost(url);
        String str=new String();
        ArrayList  localArrayList=new ArrayList();
        Iterator iterator=tempMap.entrySet().iterator();//获取迭代器
        while(iterator.hasNext()){//使用迭代器将参数map类型转为arraylist类型
            Map.Entry localEntry=(Map.Entry)iterator.next();
            localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(),(String)localEntry.getValue()));
        }
        try {
            SharedPreferences sharedPreferences=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
            if(sharedPreferences.getString("JSESSIONID", null) != null) {
                httpPost.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null)+";QINGCLOUDELB="+sharedPreferences.getString("QINGCLOUDELB",null));

            }
            httpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
            HttpResponse httpResponse=tempDefaultHttpClient1.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                 str= EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           // tempDefaultHttpClient1.getConnectionManager().shutdown();
        }
return str;
    }
    public Object Login(String url,Map parms,Context context){
        HttpPost localHttpPost=new HttpPost(url);//设置httppost方法
        ArrayList  localArrayList=new ArrayList();
        if(parms!=null){
            Iterator iterator=parms.entrySet().iterator();//获取迭代器
            while(iterator.hasNext()){//使用迭代器将参数map类型转为arraylist类型
                Map.Entry localEntry=(Map.Entry)iterator.next();
                localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(),(String)localEntry.getValue()));
            }
        }
        SharedPreferences sharedPreferences=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String[] jsee=new String[2];
        try {
            if(sharedPreferences.getString("JSESSIONID", null) != null) {
                localHttpPost.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null)+";QINGCLOUDELB="+sharedPreferences.getString("QINGCLOUDELB",null));
            }
            localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
            tempDefaultHttpClient.setRedirectHandler(new DefaultRedirectHandler(){
                @Override
                public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                    return false;
                }
            });
            HttpResponse localHttpResponse1=tempDefaultHttpClient.execute(localHttpPost);
            Log.i("as","as");
            if((localHttpResponse1.getStatusLine().getStatusCode() == 302)||(localHttpResponse1.getStatusLine().getStatusCode()==200)) {
                org.apache.http.Header[] cook= localHttpResponse1.getHeaders("Set-Cookie");

                for( Header i  : cook ){
                    if(i.getName().equals("Set-Cookie")){
                        Log.i("2",i.getName()+i.getValue());
                        String[] tempstr=i.getValue().split(";");
                        jsee[0]=tempstr[0];
                        String[] a=tempstr[0].split("=");
                        editor.putString("JSESSIONID",a[1]);
                        editor.commit();
                        break;
                    }
                }
            }else{
                Log.i("responseError",localHttpResponse1.getStatusLine().getStatusCode()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }

        return jsee;
    }
    public Object HttpPostForImage(String user_id,Context context) {
        try
        {
            DefaultHttpClient httpclient=new DefaultHttpClient();
            HttpGet localHttpGet = new HttpGet(HttpUrls.userimageurl+"?"+"xh_id="+user_id+"&"+"zplx=rxhzp");
            SharedPreferences sharedPreferences=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);

                localHttpGet.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null) + ";QINGCLOUDELB=" + sharedPreferences.getString("QINGCLOUDELB", null));
            HttpResponse localHttpResponse = httpclient.execute(localHttpGet);
            if (localHttpResponse.getStatusLine().getStatusCode() == 200)
                return BitmapFactory.decodeStream(localHttpResponse.getEntity().getContent());
            System.out.println("Error Response: " + localHttpResponse.getStatusLine().toString());
            return "";
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
            localUnsupportedEncodingException.printStackTrace();
            return "";
        }
        catch (ClientProtocolException localClientProtocolException)
        {
            localClientProtocolException.printStackTrace();
            return "";
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
        return "no internet";
    }
    public static  String getContentWithPost(Map pars,int a,Context context)  {
        String str="-1";
        if (a == 1) {
                  try {
                      DefaultHttpClient httpclient = new DefaultHttpClient();
                      HttpGet localHttpGet = new HttpGet(HttpUrls.userinfourl + "?" + "xt=jw&_=" + new Date().getTime() + "&gnmkdmKey=index&sessionUserKey=" + pars.get("sessionUserKey"));
                      SharedPreferences sharedPreferences = context.getSharedPreferences("cookies", Context.MODE_PRIVATE);
                      localHttpGet.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null) + ";QINGCLOUDELB=" + sharedPreferences.getString("QINGCLOUDELB", null));
                      localHttpGet.addHeader("accept-encoding", "gzip");
                      HttpResponse localHttpResponse = httpclient.execute(localHttpGet);
                      Log.i("entity", "d");
                      if (localHttpResponse.getStatusLine().getStatusCode() == 200) {
                          InputStreamReader isp = new InputStreamReader(localHttpResponse.getEntity().getContent());
                          //String str = GZIPtoString.Tran(localHttpResponse.getEntity());//将回应主体内容转化为utf-8格式的字符串
                          StringBuffer sb = new StringBuffer();
                          String line;
                          BufferedReader con = new BufferedReader(isp);
                          while ((line = con.readLine()) != null) {
                              sb.append(line);
                          }
                          str = new String(sb);
                          str = new String(str.getBytes(), "UTF-8");
                          Log.i("entity", str);
                          //String b="gdsfg";

                      }
                  }catch (Exception e){
                      e.printStackTrace();
                  }
        }else{
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(HttpUrls.newsListUrl+"&gnmkdmKey=index&sessionUserKey=" + pars.get("sessionUserKey"));
            Iterator iterator=pars.entrySet().iterator();//获取迭代器
            ArrayList  localArrayList=new ArrayList();
            //String str=new String();
            while(iterator.hasNext()){//使用迭代器将参数map类型转为arraylist类型
                Map.Entry localEntry=(Map.Entry)iterator.next();
                localArrayList.add(new BasicNameValuePair((String)localEntry.getKey(),(String)localEntry.getValue()));
            }
            try {
                SharedPreferences sharedPreferences=context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
                if(sharedPreferences.getString("JSESSIONID", null) != null) {
                    httpPost.setHeader("Cookie", "JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null)+";QINGCLOUDELB="+sharedPreferences.getString("QINGCLOUDELB",null));

                }
                httpPost.setEntity(new UrlEncodedFormEntity(localArrayList));
                HttpResponse httpResponse=httpclient.execute(httpPost);
                if(httpResponse.getStatusLine().getStatusCode()==200){
                    InputStreamReader isp = new InputStreamReader(httpResponse.getEntity().getContent());
                    //String str = GZIPtoString.Tran(localHttpResponse.getEntity());//将回应主体内容转化为utf-8格式的字符串
                    StringBuffer sb = new StringBuffer();
                    String line;
                    BufferedReader con = new BufferedReader(isp);
                    while ((line = con.readLine()) != null) {
                        sb.append(line);
                    }
                    str = new String(sb);
                    str = new String(str.getBytes(), "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                // tempDefaultHttpClient1.getConnectionManager().shutdown();
            }


        }
return str;
    }


}
