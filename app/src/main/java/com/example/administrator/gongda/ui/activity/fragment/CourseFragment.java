package com.example.administrator.gongda.ui.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.base.BaseFragment;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.DataCalBack;
import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.widgets.CircleProgressView;
import com.example.administrator.gongda.widgets.TimeTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CourseFragment extends BaseFragment {
    @Bind(R.id.main_timetable_ly)
    TimeTableView timeTableView;
    @Bind(R.id.course_progress)
    CircleProgressView progressBar;

    List<courses> coursesList;
    static int weekIndex=1;
    public static CourseFragment newInstance(int index){
        CourseFragment courseFragment=new CourseFragment();
        Bundle bundle=new Bundle();
        weekIndex=index;
        bundle.putInt("weekIndex", index);
        courseFragment.setArguments(bundle);
        return courseFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_course;
    }
public void getData(){
    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
    int year=sharedPreferences.getInt("chooseYear", 2016);
    int zhou=sharedPreferences.getInt("dangqianzhou",1);
    int xueqi=sharedPreferences.getInt("xueqi",1);
    int ruxuenianfen=sharedPreferences.getInt("ruxuenianfen",2014);
  // final List<courses> lists= null;
    try {
        courAsynTask asynTask=new courAsynTask(new DataCalBack() {
            @Override
            public void DataLoaded(List<courses> list) {

                timeTableView.setTimeTable(list);
                hideProgress();
            }
        });
        showProgress();
        asynTask.execute(new Integer[]{weekIndex,year,xueqi});
    } catch (Throwable throwable) {
        throwable.printStackTrace();
    }

}
    public void showProgress(){
        progressBar.setVisibility(CircleProgressView.VISIBLE);
        progressBar.spin();
        timeTableView.setVisibility(View.GONE);
    }
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
        progressBar.stopSpinning();
        timeTableView.setVisibility(View.VISIBLE);
    }
    @Override
    public void initViews() {
        getData();

    }
    class courAsynTask extends AsyncTask<Integer,String,List<courses>>{
     DataCalBack jsonCallBack;
        public courAsynTask(DataCalBack jsonCallBack){
            this.jsonCallBack=jsonCallBack;
        }


        @Override
        protected List<courses> doInBackground(Integer... params) {

            List<courses> list=new ArrayList<>();
            try {
                list= new stuDatabaseDao(getActivity()).getCourseByWeekNum(params[0],params[1],params[2]);


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
    public void onDestroyView() {
        super.onDestroyView();

    }
}
