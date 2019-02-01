package com.example.medrec_1.slider_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    WebView wViews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_views_layout);
        wViews=findViewById(R.id.webviews);
        wViews.getSettings().setJavaScriptEnabled(true);
        Intent i=getIntent();
        String url=i.getStringExtra("URL");
        wViews.loadUrl(url);
    }
}
