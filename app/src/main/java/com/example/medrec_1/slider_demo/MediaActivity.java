package com.example.medrec_1.slider_demo;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaActivity extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);
        videoView=findViewById(R.id.myVedionew);
       // videoView.setVideoPath("http://stageprogram.com/VideoFiles/2016_September_17/2016-Sep-17-00-43-47_BhojpuriNachMauryaHotelStageShowBihar.mp4");
//        String value = Bundle.getString("sks");
//        // videoView.setVideoPath(vedioPath);
//
//        Uri myUri = Uri.parse(value);
//
//        videoView.setVideoURI(myUri);
       // videoView.start();

        Intent i = getIntent();
        String videoUrl = i.getStringExtra("SENDER_KEY");
       // final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        playVideo(videoUrl);



    }
    public void playVideo(String str)
    {
        MediaController m = new MediaController(this);
        videoView.setMediaController(m);

        Uri myUri = Uri.parse(str);
        videoView.setVideoURI(myUri);
        videoView.start();
    }

}
