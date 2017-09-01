
package com.example.administrator.gongda.ui.activity;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.webkit.CookieManager;
        import android.webkit.CookieSyncManager;
        import android.webkit.DownloadListener;
        import android.webkit.WebChromeClient;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;

        import com.example.administrator.gongda.R;
        import com.example.administrator.gongda.base.BaseActivity;

        import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/29.
 */

public class Web extends BaseActivity {
    @Bind(R.id.webview)
    WebView webView;
    CookieManager cookieManager=CookieManager.getInstance();
    @Override
    public int getLayoutId() {
        return R.layout.weblayout;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String url=getIntent().getStringExtra("url");
        syncCookieToUrl(url);
        webView.loadUrl(url);
        WebSettings webSettings=webView.getSettings();
        webSettings.setUserAgentString(webSettings.getUserAgentString()+"; android_app/1.0.0");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //cookieManager.removeAllCookie();
                super.onPageFinished(view, url);

            }
        });
    }

    @Override
    public void initToolBar() {

    }
    public String syncCookieToUrl(String iniurl){

        SharedPreferences sharedPreferences=Web.this.getSharedPreferences("cookies", Context.MODE_PRIVATE);
        cookieManager.setCookie(iniurl,"JSESSIONID=" + sharedPreferences.getString("JSESSIONID", null));
        cookieManager.setCookie(iniurl,"QINGCLOUDELB="+sharedPreferences.getString("QINGCLOUDELB",null));
        String newCookies=cookieManager.getCookie(iniurl);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(Web.this);
            cookieSyncManager.sync();
        }
        return newCookies;
    }

}
