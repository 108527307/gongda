package com.example.administrator.gongda.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.gongda.NetUtils.Query;
import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.Utils.WeekUtil;
import com.example.administrator.gongda.base.BaseActivity;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.interfaces.JsonCallBack;
import com.example.administrator.gongda.widgets.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/23.
 */
public class TermselectActivity extends BaseActivity {
    @Bind(R.id.xuenian)
  Spinner xuenianSpinner;
    @Bind(R.id.daji)
    Spinner dajiSpinner;
    @Bind(R.id.term)
    Spinner termSpinner;
    @Bind(R.id.submit)
    Button button;
    @Bind(R.id.dijizhou)
    Spinner dijizhouSpinner;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collasping_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.user_image)
    CircleImageView mCircleImageView;
    private int a=-1,b=-1,c=-1,d=-1;

    @Override
    public int getLayoutId() {
        return R.layout.temp;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Map   userinfo=new HashMap();
         userinfo=new stuDatabaseDao(this).getUserInfo();
        Glide.with(this)
                .load(userinfo.get("image"))
                .asBitmap()
                .placeholder(R.drawable.ic_slide_menu_avatar_no_login)
                .into(mCircleImageView);
initData();
    }

    private void initData() {

        xuenianSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dajiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dijizhouSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==-1||b==-1||c==-1||d==-1)
                    Toast.makeText(TermselectActivity.this,"请选择后提交",Toast.LENGTH_LONG).show();
                else{
                    String userd=getIntent().getStringExtra("user_id");
                    mAsynTask asynTask=new mAsynTask(new JsonCallBack() {
                        @Override
                        public void JsonLoaded(String paramString) {
                            Intent intent=new Intent(TermselectActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    asynTask.execute(new String[]{userd});

                }
            }
        });
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar=getSupportActionBar();
        if(supportActionBar!=null){
            mCollapsingToolbarLayout.setTitle("完善信息");
        }
    }
    class mAsynTask extends AsyncTask<String,String,Boolean>{
     private JsonCallBack jsonCallBack;

        mAsynTask(JsonCallBack jsonCallBack) {
            this.jsonCallBack = jsonCallBack;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            new Cachecontrol().cacheTime(a,b,c,d,TermselectActivity.this);
            Query.queryAllGpis(params[0], getApplicationContext());
            SharedPreferences sharedPreferences=getApplication().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
            SharedPreferences sharedPreferences1=getApplication().getSharedPreferences("appinfo",EntryActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences1.edit();
            editor.putBoolean("isFirst",false).apply();
            new Cachecontrol().createWeek(TermselectActivity.this, WeekUtil.getCurrentDate(),c,d);
            int dangqina=sharedPreferences.getInt("chooseYear",2016);
            int xueqi=sharedPreferences.getInt("xueqi",1);
            Query.queryCoursesByNumber(params[0], dangqina+"", ""+(xueqi==1?3:12), getApplicationContext());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            jsonCallBack.JsonLoaded("s");
            super.onPostExecute(aBoolean);
        }
    }
}
