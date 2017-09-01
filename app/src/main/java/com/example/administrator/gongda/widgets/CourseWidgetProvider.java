package com.example.administrator.gongda.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.gongda.services.CourseService;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CourseWidgetProvider extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent=new Intent(context, CourseService.class);
        context.startService(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent intent=new Intent(context, CourseService.class);
        context.startService(intent);
//        onDeleted(context,appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Intent intent=new Intent(context, CourseService.class);
        context.startService(intent);
    }
}
