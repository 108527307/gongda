package com.example.administrator.gongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.JsHandler;
import com.example.administrator.gongda.base.BaseActivity;
import com.example.administrator.gongda.widgets.CircleProgressView;
import com.example.administrator.gongda.widgets.web.CommonWebChromeClient;
import com.example.administrator.gongda.widgets.web.CommonWebView;
import com.example.administrator.gongda.widgets.web.CommonWebViewClient;

import butterknife.Bind;

/**
 * gank.IO详情web页面
 */
public class WebActivity extends BaseActivity
{

    @Bind(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    @Bind(R.id.progress_bar)
    ProgressBar mBar;

    @Bind(R.id.web_view)
    CommonWebView mCommonWebView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private static final String KEY_URL = "url";

    private static final String KEY_TITLE = "title";

    private String url, title;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_web;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        Intent intent = getIntent();
        if (intent != null)
            parseIntent(intent);

        initWebSetting();

        hideProgress();
        mCommonWebView.setWebChromeClient(new CommonWebChromeClient(mBar, mCircleProgressView));
        mCommonWebView.setWebViewClient(new CommonWebViewClient(WebActivity.this));
        mCommonWebView.loadUrl(url);
    }

    @Override
    public void initToolBar()
    {

        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        //getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {

        super.onNewIntent(intent);
        parseIntent(intent);
    }

    private void parseIntent(Intent intent)
    {

        url = intent.getStringExtra(KEY_URL);
        title = intent.getStringExtra(KEY_TITLE);

        if (TextUtils.isEmpty(url))
        {
            finish();
        }
    }

    private void initWebSetting()
    {

        JsHandler jsHandler = new JsHandler(this, mCommonWebView);
        mCommonWebView.addJavascriptInterface(jsHandler, "JsHandler");
    }

    public static boolean start(Activity activity, String url, String title)
    {

        Intent intent = new Intent();
        intent.setClass(activity, WebActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);

        return true;
    }

    public static boolean start(Activity activity, String url)
    {

        return start(activity, url, null);
    }


    public void hideProgress()
    {

        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }



}