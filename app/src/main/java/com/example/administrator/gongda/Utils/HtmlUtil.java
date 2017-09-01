package com.example.administrator.gongda.Utils;

import com.example.administrator.gongda.NetUtils.HttpUrls;
import com.example.administrator.gongda.bean.newsBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/9/25.
 */
public class HtmlUtil {
    public List getHtmlObject(String content, boolean isUserinfo){
        List<String> str=new ArrayList<>();
        Document doc=Jsoup.parse(content);
        if(isUserinfo){
            Elements media_body=doc.getElementsByClass("media-body");
            Elements media_heading=doc.getElementsByClass("media-heading");
            //Elements fs2=doc.getElementsByClass("fs2");
            String  name=media_heading.get(0).text();
           // String  con=fs2.get(0).text();
            Document doc2=Jsoup.parse(media_body.toString());
            Elements temp=doc2.getElementsByTag("p");
            Element  jieshao=temp.get(0);
            String xueyuan=jieshao.text();
            str.add(name);
            str.add(xueyuan);
           // str.add(con);
            return str;
        }else{
            List<newsBean> list=new ArrayList<>();
            Elements listGroup=doc.getElementsByTag("a");
            for(Element i : listGroup){
                String url= HttpUrls.newshost+i.attr("href");
                String title=i.getElementsByClass("title").text();
                String time=i.getElementsByClass("time").text();
                list.add(new newsBean(title,time,url,1));
            }
            return list;
        }

    }
    public List getWorks(String url) throws IOException {

        Document document = Jsoup.connect(url).get();
        return null;
    }
}
