package com.example.administrator.gongda.ui.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.administrator.gongda.NetUtils.HttpUrls;
import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.SnackbarUtil;
import com.example.administrator.gongda.base.BaseActivity;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;
import com.example.administrator.gongda.services.CourseService;
import com.example.administrator.gongda.ui.activity.fragment.GradeFragment;
import com.example.administrator.gongda.ui.activity.fragment.HomeFragment;
import com.example.administrator.gongda.ui.activity.fragment.NewsAndOther;
import com.example.administrator.gongda.widgets.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    @Bind(R.id.coor_layout)
    CoordinatorLayout mCoordinatorLayout;
    CircleImageView mUserAvatar;
    TextView mUserName;
    TextView mUserBio;
    private long exitTime=0;
    private Fragment[] fragments;
    private HomeFragment homeFragment;
    private GradeFragment gradeFragment;
    private NewsAndOther newsAndOtherFragment;
    private int currentTabIndex;
    private int index;
    private stuDatabaseDao databaseDao;
    Map userinfo=new HashMap();
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Log.d("scott", "on receive action="+intent.getAction());
            String action = intent.getAction();
            if (action.equals("com.scott.sayhi"))
            {
              finish();
            }
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
private void setupDrawerContent(NavigationView mNavigationView){
    View headerView=mNavigationView.getHeaderView(0);
    mUserAvatar= (CircleImageView) headerView.findViewById(R.id.github_user_avatar);
    mUserName= (TextView) headerView.findViewById(R.id.github_user_name);
    mUserBio= (TextView) headerView.findViewById(R.id.github_user_bio);
}
    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent= new Intent("com.example.administrator.services.CourseService");
        intent.setClass(this,CourseService.class);
       startService(intent);
//        Notification notification=new Notification(R.drawable.ic_launcher,"dd",System.currentTimeMillis());
//        PendingIntent pendingIntent=PendingIntent.getService(this,0,intent,0);
//        not

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.scott.sayhi");
        registerReceiver(mBroadcastReceiver, filter);
        databaseDao=new stuDatabaseDao(MainActivity.this);
  if(mNavigationView!=null){
      setupDrawerContent(mNavigationView);
  }

        homeFragment=HomeFragment.newInstance();
        newsAndOtherFragment=NewsAndOther.newInstance();
        gradeFragment=GradeFragment.newInstance("2016","1");
        fragments=new Fragment[]{homeFragment,gradeFragment,newsAndOtherFragment};
        getSupportFragmentManager().beginTransaction().replace(R.id.content,homeFragment).commit();
        setUserInfo();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
               switch(item.getItemId()){
                   case R.id.nav_home:changNavItem(item,0,"课程表",true);
                       return true;
                   case R.id.nav_my_focus:
                       changNavItem(item,1,"成绩",false);
                       return true;
                   case R.id.nac_news_other:
                       changNavItem(item,2,"教务信息",false);
                       return true;
                   case R.id.moodle:
                      Intent intent=new Intent(MainActivity.this,WebActivity.class);
                       intent.putExtra("url", HttpUrls.moodleurl);
                       intent.putExtra("title","南工在线学习平台");
                       startActivity(intent);
                       return true;
                   case R.id.music:
                       Intent intent2=new Intent(MainActivity.this,WebActivity.class);
                       intent2.putExtra("url",HttpUrls.musicurl);
                       intent2.putExtra("title","校内免费音乐");
                       startActivity(intent2);
                       return true;
                   case R.id.online:
                       Intent intent1=new Intent(MainActivity.this,WebActivity.class);
                       intent1.putExtra("url",HttpUrls.online);
                       intent1.putExtra("title","南工在线");
                       startActivity(intent1);
                       return true;
                   case R.id.nav_about:
                       MyInfoActivity.launch(userinfo,MainActivity.this);
                      return true;
                   case R.id.nav_about_app:
                       aboutApp.launch(MainActivity.this);
                       return true;
                   case R.id.PowerQuerying:
                       Intent intent0=new Intent(MainActivity.this,WebActivity.class);
                       intent0.putExtra("url",HttpUrls.powerurl);
                       intent0.putExtra("title","水电查询与充值");
                       startActivity(intent0);
                       return true;
               }
                return true;
            }
        });
    }

    private void changNavItem(MenuItem item, int i, String name, boolean b) {
        index=i;
        addFragment();
        item.setChecked(true);
        mToolbar.setTitle(name);
        mDrawerLayout.closeDrawers();
    }

    private void addFragment() {
        android.support.v4.app.FragmentTransaction trx=getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if(!fragments[index].isAdded())
            trx.add(R.id.content,fragments[index]);
        trx.show(fragments[index]).commit();
        currentTabIndex=index;
    }

    public void setUserInfo(){
     userinfo=databaseDao.getUserInfo();
    Glide.with(this)
            .load(userinfo.get("image"))
            .asBitmap()
            .placeholder(R.drawable.ic_slide_menu_avatar_no_login)
            .into(new BitmapImageViewTarget(mUserAvatar));
    mUserName.setText((String) userinfo.get("name"));
    mUserBio.setText((String)userinfo.get("sessionUserKey"));

}
    @Override
    public void initToolBar() {
   mToolbar.setTitle("工大盒子");
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,mToolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();//将drawertoggle与acitonbar关联，将开关的图片显示在action上
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
                logoutApp();
        }

        return false;
    }

    private void logoutApp()
    {

        if (System.currentTimeMillis() - exitTime > 2000)
        {
            SnackbarUtil.showMessage(mCoordinatorLayout, "再按一次退出工大盒子");
            exitTime = System.currentTimeMillis();
        } else
        {
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBroadcastReceiver);
    }
}
