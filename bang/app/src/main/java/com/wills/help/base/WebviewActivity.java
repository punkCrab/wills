package com.wills.help.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wills.help.R;

/**
 * com.wills.help.base
 * Created by lizhaoyong
 * 2017/1/6.
 */

public class WebViewActivity extends BaseActivity{
    private ProgressBar pb;
    private WebView wv;
    private String url;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setBaseView(R.layout.activity_webview);
        url = getIntent().getExtras().getString("url");
        pb = (ProgressBar) findViewById(R.id.pb);
        wv = (WebView) findViewById(R.id.wv);
        initWebView(wv);
        display();
    }

    private void initWebView(WebView wv){
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setBaseTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                } else {
                    if (pb.getVisibility() == View.GONE)
                        pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                }
            }
        });
    }

    private void display(){
        if (!TextUtils.isEmpty(url))
            wv.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();// 返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
