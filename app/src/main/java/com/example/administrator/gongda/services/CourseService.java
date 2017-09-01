package com.example.administrator.gongda.services;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.DataCalBack;
import com.example.administrator.gongda.widgets.CourseWidgetProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CourseService extends Service{
    String TAG_turnLeft="com.example.administrator.services.CoureService.turnleft";
    String TAG_turnRight="com.example.administrator.services.CourseService.turnright";
    int weekindex=0;
    private HashMap<String,String>map=new HashMap<>();
    AppWidgetManager appWidgetManager;
    ComponentName componentName;
    RemoteViews remoteViews;

    @Override
    public void onCreate() {
        super.onCreate();
        componentName=new ComponentName(CourseService.this, CourseWidgetProvider.class);
        appWidgetManager = AppWidgetManager.getInstance(this);
        remoteViews=new RemoteViews(getPackageName(), R.layout.course_widget);

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(TAG_turnLeft);
        intentFilter.addAction(TAG_turnRight);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction=intent.getAction();
            if(TAG_turnLeft.equals(intentAction))
            updataView(0);
            if(TAG_turnRight.equals(intentAction))
                updataView(1);
            else if(!isServiceRunning("com.example.administrator.gongda.services.CourseService")){
                updataView(2);

            }
//            Intent mService = new Intent(getApplicationContext(), CourseService.class);
//            stopService(mService ); //停止服务
//            Intent intent1=new Intent(getApplicationContext(),CourseService.class);
//            getApplication().startService(intent1);

        }
    };
    private boolean isServiceRunning(String serviceName){
        ActivityManager am= (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos=am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info : infos){
            Log.i("Servicesname",info.service.getClassName());
            if(serviceName.equals(info.service.getClassName())) {

                return true;
            }
        }
        return false;
    }
    private void updataView(int i) {


             if(i==0){
                 if(weekindex==1)
                     Toast.makeText(this,"已到第一周，不可向前查询",Toast.LENGTH_LONG).show();
                 else
                     weekindex--;
             }
            if(i==1){
                if(weekindex==21)
                    Toast.makeText(this,"已到最后一周周，不可向后查询",Toast.LENGTH_LONG).show();
                else
                    weekindex++;
            }
        if(i==2)
            weekindex=new Cachecontrol().getWeek(getApplicationContext())+1;
          getData();
    }
    private void clearRemoteView(){
        if((map.get("mon12"))!=null)
            remoteViews.setTextViewText(R.id.mon12,"");
        if((map.get("mon34"))!=null)
            remoteViews.setTextViewText(R.id.mon34,"");
        if((map.get("mon56"))!=null)
            remoteViews.setTextViewText(R.id.mon56,"");
        if((map.get("mon78"))!=null)
            remoteViews.setTextViewText(R.id.mon78,"");
        if((map.get("mon910"))!=null)
            remoteViews.setTextViewText(R.id.mon910,"");
        if((map.get("mon1112"))!=null)
            remoteViews.setTextViewText(R.id.mon1112,"");
        if((map.get("tues12"))!=null)
            remoteViews.setTextViewText(R.id.tues12,"");
        if((map.get("tues34"))!=null)
            remoteViews.setTextViewText(R.id.tues34,"");
        if((map.get("tues56"))!=null)
            remoteViews.setTextViewText(R.id.tues56,"");
        if((map.get("tues78"))!=null)
            remoteViews.setTextViewText(R.id.tues78,"");
        if((map.get("tues910"))!=null)
            remoteViews.setTextViewText(R.id.tues910,"");
        if((map.get("tues1112"))!=null)
            remoteViews.setTextViewText(R.id.tues1112,"");
        if((map.get("wed12"))!=null)
            remoteViews.setTextViewText(R.id.wed12,"");
        if((map.get("wed34"))!=null)
            remoteViews.setTextViewText(R.id.wed34,"");
        if((map.get("wed56"))!=null)
            remoteViews.setTextViewText(R.id.wed56,"");
        if((map.get("wed78"))!=null)
            remoteViews.setTextViewText(R.id.wed78,"");
        if((map.get("wed910"))!=null)
            remoteViews.setTextViewText(R.id.wed910,"");
        if((map.get("wed1112"))!=null)
            remoteViews.setTextViewText(R.id.wed1112,"");
        if((map.get("thurs12"))!=null)
            remoteViews.setTextViewText(R.id.thurs12,"");
        if((map.get("thurs34"))!=null)
            remoteViews.setTextViewText(R.id.thurs34,"");
        if((map.get("thurs56"))!=null)
            remoteViews.setTextViewText(R.id.thurs56,"");
        if((map.get("thurs78"))!=null)
            remoteViews.setTextViewText(R.id.thurs78,"");
        if((map.get("thurs910"))!=null)
            remoteViews.setTextViewText(R.id.thurs910,"");
        if((map.get("thurs1112"))!=null)
            remoteViews.setTextViewText(R.id.thurs1112,"");
        if((map.get("fri12"))!=null)
            remoteViews.setTextViewText(R.id.fri12,"");
        if((map.get("fri34"))!=null)
            remoteViews.setTextViewText(R.id.fri34,"");
        if((map.get("fri56"))!=null)
            remoteViews.setTextViewText(R.id.fri56,"");
        if((map.get("fri78"))!=null)
            remoteViews.setTextViewText(R.id.fri78,"");
        if((map.get("fri910"))!=null)
            remoteViews.setTextViewText(R.id.fri910,"");
        if((map.get("fri1112"))!=null)
            remoteViews.setTextViewText(R.id.fri1112,"");
        if((map.get("sat12"))!=null)
            remoteViews.setTextViewText(R.id.sat12,"");
        if((map.get("sat34"))!=null)
            remoteViews.setTextViewText(R.id.sat34,"");
        if((map.get("sat56"))!=null)
            remoteViews.setTextViewText(R.id.sat56,"");
        if((map.get("sat78"))!=null)
            remoteViews.setTextViewText(R.id.sat78,"");
        if((map.get("sat910"))!=null)
            remoteViews.setTextViewText(R.id.sat910,"");
        if((map.get("sat1112"))!=null)
            remoteViews.setTextViewText(R.id.sat1112,"");
        if((map.get("sun12"))!=null)
            remoteViews.setTextViewText(R.id.sun12,"");
        if((map.get("sun34"))!=null)
            remoteViews.setTextViewText(R.id.sun34,"");
        if((map.get("sun56"))!=null)
            remoteViews.setTextViewText(R.id.sun56,"");
        if((map.get("sun78"))!=null)
            remoteViews.setTextViewText(R.id.sun78,"");
        if((map.get("sun910"))!=null)
            remoteViews.setTextViewText(R.id.sun910,"");
        if((map.get("sun1112"))!=null)
            remoteViews.setTextViewText(R.id.sun1112,"");
        appWidgetManager.updateAppWidget(componentName,remoteViews);
        map.clear();
    }
    public void getData(){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
        int year=sharedPreferences.getInt("chooseYear", 2016);
        int xueqi=sharedPreferences.getInt("xueqi",1);

        try {
            courAsynTask asynTask=new courAsynTask(new DataCalBack() {
                @Override
                public void DataLoaded(List<courses> list) {
                    clearRemoteView();
                 for(courses c : list){
                  manageTableView(c);
                 }
//                    remoteViews.setTextViewText(R.id.left,"<");
//                    remoteViews.setTextViewText(R.id.right,">");
                    remoteViews.setTextViewText(R.id.week,weekindex+"");
           appWidgetManager.updateAppWidget(componentName,remoteViews);
                }
            });
           // showProgress();
            asynTask.execute(new Integer[]{weekindex,year,xueqi});
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
    private boolean judgeCourseLength(courses course){
        if((course.getEndnum()-course.getStartnum())>1){
            return false;
        }else return true;
    }
  private void manageTableView(courses course){
     // remoteViews.removeAllViews(R.layout.course_widget);
     // remoteViews=new RemoteViews(getPackageName(), R.layout.course_widget);





             switch(course.getXinqiji()){
                 case "1":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.mon12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon12","1");
                             if(judgeCourseLength(course))
                                 break;
                         case 3: remoteViews.setTextViewText(R.id.mon34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.mon56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon56","1");
                         if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.mon78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.mon910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon910","1");
                             if(judgeCourseLength(course))
                                 break;
                         case 10: remoteViews.setTextViewText(R.id.mon1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("mon1112","1");break;
                         default:break;
                     }
                     break;
                 case "2":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.tues12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues12","1");break;
                         case 3: remoteViews.setTextViewText(R.id.tues34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.tues56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues56","1");break;
                         case 7: remoteViews.setTextViewText(R.id.tues78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.tues910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues910","1");break;
                         case 10: remoteViews.setTextViewText(R.id.tues1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("tues1112","1");break;
                         default:break;
                     }
                     break;
                 case "3":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.wed12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed12","1");if(judgeCourseLength(course))
                             break;
                         case 3: remoteViews.setTextViewText(R.id.wed34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.wed56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed56","1");if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.wed78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.wed910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed910","1");if(judgeCourseLength(course))
                             break;
                         case 10: remoteViews.setTextViewText(R.id.wed1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("wed1112","1");break;
                         default:break;
                     }
                     break;
                 case "4":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.thurs12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs12","1");if(judgeCourseLength(course))
                             break;
                         case 3: remoteViews.setTextViewText(R.id.thurs34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.thurs56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs56","1");if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.thurs78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.thurs910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs910","1");if(judgeCourseLength(course))
                             break;
                         case 10: remoteViews.setTextViewText(R.id.thurs1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("thurs910","1");break;
                         default:break;
                     }
                     break;
                 case "5":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.fri12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri12","1");if(judgeCourseLength(course))
                             break;
                         case 3: remoteViews.setTextViewText(R.id.fri34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.fri56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri56","1");if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.fri78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.fri910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri910","1");if(judgeCourseLength(course))
                             break;
                         case 10: remoteViews.setTextViewText(R.id.fri1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("fri1112","1");break;
                         default:break;
                     }
                     break;
                 case "6":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.sat12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat12","1");if(judgeCourseLength(course))
                             break;
                         case 3: remoteViews.setTextViewText(R.id.sat34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.sat56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat56","1");if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.sat78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.sat910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat910","1");if(judgeCourseLength(course))
                             break;
                         case 10: remoteViews.setTextViewText(R.id.sat1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sat1112","1");break;
                         default:break;
                     }
                     break;
                 case "7":
                     switch(course.getStartnum()){
                         case 1: remoteViews.setTextViewText(R.id.sun12,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun12","1");if(judgeCourseLength(course))
                             break;
                         case 3: remoteViews.setTextViewText(R.id.sun34,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun34","1");break;
                         case 5: remoteViews.setTextViewText(R.id.sun56,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun56","1");if(judgeCourseLength(course))
                             break;
                         case 7: remoteViews.setTextViewText(R.id.sun78,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun78","1");break;
                         case 9: remoteViews.setTextViewText(R.id.sun910,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun910","1");if(judgeCourseLength(course))
                             break;
                         case 10: remoteViews.setTextViewText(R.id.sun1112,course.getKechenmingchen()+"@"+course.getJiangshi()+"@"+course.getDidian());map.put("sun1112","1");break;
                         default:break;
                     }
                     break;
             }
  }

    class courAsynTask extends AsyncTask<Integer,String,List<courses>> {
        DataCalBack jsonCallBack;
        public courAsynTask(DataCalBack jsonCallBack){
            this.jsonCallBack=jsonCallBack;
        }


        @Override
        protected List<courses> doInBackground(Integer... params) {

            List<courses> list=new ArrayList<>();
            try {

                list= new stuDatabaseDao(getApplicationContext()).getCourseByWeekNum(params[0],params[1],params[2]);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<courses> courses) {
            this.jsonCallBack.DataLoaded(courses);
            super.onPostExecute(courses);


        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updataView(2);
        Intent prevInten = new Intent();
        prevInten.setAction(TAG_turnLeft);
        // 用Intent实例化一个PendingIntent
        PendingIntent Pprevintent= PendingIntent.getBroadcast(this, 0,
                prevInten, 0);
        remoteViews.setOnClickPendingIntent(R.id.left, Pprevintent);
        Intent prevInten2 = new Intent();
        prevInten2.setAction(TAG_turnRight);
        // 用Intent实例化一个PendingIntent
        PendingIntent Pprevintent2= PendingIntent.getBroadcast(this, 0,
                prevInten2, 0);
        remoteViews.setOnClickPendingIntent(R.id.right, Pprevintent2);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        Intent intent=new Intent(getApplicationContext(),CourseService.class);
        getApplication().startService(intent);
    }
}
