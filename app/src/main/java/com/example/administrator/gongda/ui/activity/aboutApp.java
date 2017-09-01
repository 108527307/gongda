package com.example.administrator.gongda.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/30.
 */

public class aboutApp extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.version)
    TextView VersionText;
    @Override
    public int getLayoutId()
    {

        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {
       VersionText.setText(getVersion());
    }
      public static void launch(Context context){
          Intent intent=new Intent(context,aboutApp.class);
          context.startActivity(intent);
      }
    public String getVersion() {
             try {
                     PackageManager manager = this.getPackageManager();
                     PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                    String version = info.versionName;
                     return "版本号：" + version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return "版本号：未知";
                 }
         }
     @Override
    public void initToolBar()
    {

        mToolbar.setTitle("关于工大盒子");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
