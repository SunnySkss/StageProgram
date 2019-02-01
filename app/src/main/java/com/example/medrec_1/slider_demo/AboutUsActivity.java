package com.example.medrec_1.slider_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class AboutUsActivity extends AppCompatActivity {
    public class ContactActivity  extends AppCompatActivity {
        private WebView wView;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.aboutus);
            wView=findViewById(R.id.wview);
            wView.getSettings().setJavaScriptEnabled(true);
            wView.loadUrl("http://stageprogram.com/About");

        }
    }
}
