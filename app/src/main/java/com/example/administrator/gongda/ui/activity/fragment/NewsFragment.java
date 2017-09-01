package com.example.administrator.gongda.ui.activity.fragment;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.gongda.NetUtils.Httputil;
import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Tasks.localAsynTask;
import com.example.administrator.gongda.Utils.HtmlUtil;
import com.example.administrator.gongda.Utils.SnackbarUtil;
import com.example.administrator.gongda.adapters.NewsListAdapter;
import com.example.administrator.gongda.base.BaseFragment;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.JsonCallBack;
import com.example.administrator.gongda.interfaces.newsCallBack;
import com.example.administrator.gongda.bean.newsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/27.
 */

public class NewsFragment extends BaseFragment {
    @Bind(R.id.swipe_refreshb)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycleb)
    RecyclerView recyclerView;
    private List<newsBean> newlist=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private NewsListAdapter newsListAdapter;
    private stuDatabaseDao databaseDao;
    @Override
    public int getLayoutId() {
        return R.layout.news;
    }
public static NewsFragment Instance(){
    return new NewsFragment();
}
    @Override
    public void initViews() {
        databaseDao=new stuDatabaseDao(getActivity());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getdata();
            }
        });
        initRecycleView();
        showProgress();
    }
    private void initRecycleView(){
      linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        newsListAdapter=new NewsListAdapter(getActivity(),newlist);
        recyclerView.setAdapter(newsListAdapter);


    }
    private void showProgress(){
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getdata();
            }
        },500);
    }
    private void getdata() {

       final newAsynTask asynTask=new newAsynTask(new newsCallBack() {
            @Override
            public void newsLoaded(List<newsBean> list) {
                if(list.size()==0)
                    SnackbarUtil.showMessage(recyclerView,"未搜索到教务信息");
                else {
                    newlist=list;
                    newsListAdapter.upData(list);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        localAsynTask asynTasks=new localAsynTask(new JsonCallBack() {
            @Override
            public void JsonLoaded(String paramString) {
                asynTask.execute("s");
            }
        },"no",getActivity());
        asynTasks.execute(new String[]{"no"});

    }
    class newAsynTask extends AsyncTask<String,String,List<newsBean>>{
      private newsCallBack callBack;
        public newAsynTask(newsCallBack callBack){
            this.callBack=callBack;
        }
        @Override
        protected List<newsBean> doInBackground(String... params) {

            Map temp=new HashMap();
            List<newsBean> list=new ArrayList<>();
          String user_id= (String) databaseDao.getUserInfo().get("sessionUserKey");
            temp.put("sessionUserKey",user_id);
            try {
                 list= new HtmlUtil().getHtmlObject(Httputil.getContentWithPost(temp,2,getActivity()),false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<newsBean> list) {
            callBack.newsLoaded(list);
            super.onPostExecute(list);
        }
    }
}
