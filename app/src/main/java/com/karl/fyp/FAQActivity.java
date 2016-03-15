package com.karl.fyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

/**
 * Copyright Karl jones 2016.
 * FAQActivity
 *
 * This shows the user FAQ information for the application.
 */

public class FAQActivity extends AppCompatActivity {

    WebView FAQWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        FAQWebView = (WebView) findViewById(R.id.webview_faq);
        FAQWebView.loadUrl("file:///android_asset/html/faq.html");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if(FAQWebView.canGoBack()){
                        FAQWebView.goBack();
                    } else {
                        finish();
                    }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
