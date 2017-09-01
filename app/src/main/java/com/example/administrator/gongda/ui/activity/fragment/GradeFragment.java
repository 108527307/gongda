package com.example.administrator.gongda.ui.activity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.SnackbarUtil;
import com.example.administrator.gongda.adapters.gradeListAdapter;
import com.example.administrator.gongda.base.BaseFragment;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.AgpCallBack;
import com.example.administrator.gongda.bean.gpis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/21.
 */
public class GradeFragment extends BaseFragment {
    @Bind(R.id.gpa)
    TextView gpaView;
    @Bind(R.id.term)
    Spinner termSpinner;
    @Bind(R.id.swipe_refresha)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclea)
    RecyclerView recyclerView;
    Boolean isRefresh;
    String[] terms = new String[]{"全部学期", "大一上", "大一下", "大二上", "大二下", "大三上", "大三下", "大四上", "大四下"};
    int termIndex = 0;
    private List<gpis> gpalist=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
  private stuDatabaseDao databaseDao;
   private  gradeListAdapter gradelistadapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_grade;
    }

    public static GradeFragment newInstance(String xuenian, String xueqi) {
        GradeFragment gradeFragment = new GradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("xuenian", xuenian);
        bundle.putString("xueqi", xueqi);
        gradeFragment.setArguments(bundle);
        return gradeFragment;
    }

    @Override
    public void initViews() {
        databaseDao=new stuDatabaseDao(getActivity());
        initSpinner();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                               getData(termIndex);
            }
        });
        initRecyclerView();

    }

    private void initSpinner() {
        List<String> termlist = new ArrayList<>();
        for (String i : terms)
            termlist.add(i);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.termspinner_item, R.id.termitem, termlist);
        termSpinner.setAdapter(adapter);

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                termIndex = position;
                showProgress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
private void initRecyclerView(){
    linearLayoutManager=new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);
     gradelistadapter=new gradeListAdapter(getActivity(),gpalist);
    recyclerView.setAdapter(gradelistadapter);
}
    private void showProgress() {


        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                isRefresh = true;
                getData(termIndex);
            }
        }, 500);


    }

    private void getData(int termIndex) {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
        int ruxuenianfen=sharedPreferences.getInt("ruxuenianfen",2014);
        String[] str=new String[2];
        switch (termIndex){
            case 0:str[0]=str[1]="-1";break;
            case 1:str[0]=String.valueOf(ruxuenianfen);
                   str[1]=1+"";break;
            case 2:str[0]=String.valueOf(ruxuenianfen);
                str[1]=2+"";
                break;
            case 3:str[0]=String.valueOf(ruxuenianfen+1);
                str[1]=1+"";
                break;
            case 4:str[0]=String.valueOf(ruxuenianfen+1);
                str[1]=2+"";
                break;
            case 5:str[0]=String.valueOf(ruxuenianfen+2);
                str[1]=1+"";
                break;
            case 6:str[0]=String.valueOf(ruxuenianfen+2);
                str[1]=2+"";
                break;
            case 7:str[0]=String.valueOf(ruxuenianfen+3);
                str[1]=1+"";
                break;
            case 8:str[0]=String.valueOf(ruxuenianfen+3);
                str[1]=2+"";
                break;
        }
        GpaLocalAsynTask gpaLocalAsynTask=new GpaLocalAsynTask(new AgpCallBack() {
            @Override
            public void DataLoaded(List<gpis> gpaList) {
                swipeRefreshLayout.setRefreshing(false);
                  if (gpaList.size()==0)
                      SnackbarUtil.showMessage(recyclerView,"未搜索到该学期考试结果");
                else{
                      gpalist=gpaList;
                      gradelistadapter.UpData(gpaList);
                      calGPA();
                  }
            }
        });
        gpaLocalAsynTask.execute(str);
    }

    private void calGPA() {
       Double sumXueFen_JiDian=0.0;
        Double sumXueFen=0.0;
        for(gpis i : gpalist){
           sumXueFen_JiDian+=Double.parseDouble(i.getShijian())*Double.parseDouble(i.getJidian());
            sumXueFen+=Double.parseDouble(i.getShijian());
        }
        DecimalFormat dcmFmt=new DecimalFormat("0.00");
        Double AVG_GPA=sumXueFen_JiDian/sumXueFen;
        gpaView.setText(dcmFmt.format(AVG_GPA)+"");
    }

    class GpaLocalAsynTask extends AsyncTask<String,String,List<gpis>>{
AgpCallBack agpCallBack;
       public GpaLocalAsynTask(AgpCallBack agpCallBack){
           this.agpCallBack=agpCallBack;
       }
        @Override
        protected List<gpis> doInBackground(String... params) {
            List<gpis> list=new ArrayList<>();
           if(params[0].equals("-1"))
               list=databaseDao.showAllGpa();
            else{
               list=databaseDao.showAllgpisById(params[0],params[1]);
           }
            return list;
        }

        @Override
        protected void onPostExecute(List<gpis> gpises) {
            agpCallBack.DataLoaded(gpises);
            super.onPostExecute(gpises);
        }
    }
}
