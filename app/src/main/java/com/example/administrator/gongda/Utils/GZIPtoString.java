package com.example.administrator.gongda.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.util.ByteArrayBuffer;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2016/9/29.
 */

public class GZIPtoString {
    public static String Tran(HttpEntity httpEntity){
        String str="n";
        try {
            ByteArrayBuffer byteArrayBuffer=new ByteArrayBuffer(4096);
            GZIPInputStream gzipInputStream=new GZIPInputStream(httpEntity.getContent());
            int i;
            byte[] temp=new byte[4096];
            while ((i=gzipInputStream.read(temp))!=-1)
                byteArrayBuffer.append(temp,0,i);
            str=new String(byteArrayBuffer.toByteArray(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }
}
