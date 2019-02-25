package com.example.medrec_1.slider_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
    WebView wViews;
    TextView titletxt;
    private ImageButton webimg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_views_layout);
        wViews=findViewById(R.id.webviews);
        titletxt=findViewById(R.id.webTitle);
        webimg=findViewById(R.id.webimgbac);
        wViews.getSettings().setJavaScriptEnabled(true);
        Intent i=getIntent();
        String url=i.getStringExtra("URL");
        String dtail=i.getStringExtra("name");
        titletxt.setText(dtail);
        wViews.loadUrl(url);
        webimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
