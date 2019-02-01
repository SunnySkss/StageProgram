package com.example.medrec_1.slider_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class ContactActivity  extends AppCompatActivity {
    WebView wView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertcontact);
        wView=findViewById(R.id.wview);
        wView.getSettings().setJavaScriptEnabled(true);
        wView.loadUrl("http://stageprogram.com/Contact");

    }
}
