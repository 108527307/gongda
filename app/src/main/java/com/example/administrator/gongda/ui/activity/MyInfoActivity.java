package com.example.administrator.gongda.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.base.BaseActivity;
import com.example.administrator.gongda.widgets.CircleImageView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/29.
 */

public class MyInfoActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collasping_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.user_image)
    CircleImageView mCircleImageView;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.major)
    TextView major;
    @Bind(R.id.banji)
    TextView banji;
    @Bind(R.id.con)
    TextView con;
    @Bind(R.id.signout)
    FloatingActionButton signout;
    @Override
    public int getLayoutId() {
        return R.layout.myinfo_activity;
    }
  public static void launch(Map map, Context context){

      ArrayList<String> user=new ArrayList<>();
      user.add((String) map.get("name"));
      user.add((String) map.get("xueyuan"));
      user.add("con");
      user.add((String) map.get("image"));
      Bundle bundle=new Bundle();

      bundle.putStringArrayList("currentuserinfo",user);
      Intent intent=new Intent(context,MyInfoActivity.class);
      intent.putExtra("currentuserinfo",bundle);
      context.startActivity(intent);

  }
    @Override
    public void initViews(Bundle savedInstanceState) {
     setInfo();
    }

    private void setInfo() {
        ArrayList<String> temp=getIntent().getBundleExtra("currentuserinfo").getStringArrayList("currentuserinfo");
        Glide.with(MyInfoActivity.this)
                .load(temp.get(3))
                .asBitmap()
                .placeholder(R.drawable.ic_slide_menu_avatar_no_login)
                .into(mCircleImageView);
        name.setText(temp.get(0));
        String str[]=temp.get(1).split(" ");
        major.setText(str[0]);
        banji.setText(str[1]);
       // con.setText(temp.get(2));
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Cachecontrol().finishAllInfo(MyInfoActivity.this);
                Intent intent=new Intent(MyInfoActivity.this,LoginActivity.class);
                startActivity(intent);
                Intent intent1 = new Intent();
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setAction("com.scott.sayhi");
                sendBroadcast(intent1);
                finish();

            }
        });
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar=getSupportActionBar();
        if(supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            mCollapsingToolbarLayout.setTitle("我的信息");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
