package com.example.medrec_1.slider_demo;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.medrec_1.slider_demo.utils.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MediaActivity extends AppCompatActivity {

    VideoView videoView;
    TextView likes, dislikes,vedioTital,totViews,vedioDateTime,vedioDesc;
    CreateUserResponse mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);
        videoView = findViewById(R.id.myVedionew);
        likes = findViewById(R.id.likesVedio);
        dislikes = findViewById(R.id.dislikeVedio);
        vedioTital=findViewById(R.id.vedioTital);
        totViews=findViewById(R.id.TotViews);
        vedioDateTime=findViewById(R.id.vedioDateTime);
        vedioDesc=findViewById(R.id.vedioDesc);
        // videoView.setVideoPath("http://stageprogram.com/VideoFiles/2016_September_17/2016-Sep-17-00-43-47_BhojpuriNachMauryaHotelStageShowBihar.mp4");
//        String value = Bundle.getString("sks");
//        // videoView.setVideoPath(vedioPath);
//
//        Uri myUri = Uri.parse(value);
//
//        videoView.setVideoURI(myUri);
        // videoView.start();

        if (getIntent() != null && getIntent().hasExtra("data")) {
            mData = getIntent().getParcelableExtra("data");
        }
        Intent i = getIntent();
        //String videoUrl = i.getStringExtra("mediaUrl");
        //int totlikes = i.getIntExtra("likesVed", 0);
        //int totdislike = i.getIntExtra("dislikeVed", 1);


        String videoUrl = Constant.VIDEO_URL +mData.getMediaUrl();
        int totlikes = mData.getTotalLike();
        int totdislike =mData.getTotalDislike();

        final String dateTime = mData.getCreatedDate();

       // ZonedDateTime d = ZonedDateTime.parse(dateTime);

     /*   DateTimeFormatter formatter = null;
        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SX");
        } catch (Exception e) {
            e.printStackTrace();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        final ZonedDateTime parsed = ZonedDateTime.parse(dateTime);
*/
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

        DateTimeFormatter formatter;
        String dateString = "2010-03-01T00:00:00-08:00";
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = dtf.parseDateTime(dateString);
        System.out.println(dateTime);


        String datenTime = mData.getCreatedDate();
        String[] parts = datenTime.split("T");
        String part1 = parts[0];
        String part2 = parts[1];
        // final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        likes.setText(String.valueOf(totlikes));
        dislikes.setText(String.valueOf(totdislike));
        vedioTital.setText(mData.getVideoTitle());
        totViews.setText(String.valueOf(mData.getTotalViews())+"views");
        vedioDateTime.setText(mData.getCreatedDate());
        vedioDesc.setText(mData.getVideoDescription());
        playVideo(videoUrl);


    }

    public void playVideo(String str) {
        MediaController m = new MediaController(this);
        videoView.setMediaController(m);

        Uri myUri = Uri.parse(str);
        videoView.setVideoURI(myUri);
        videoView.start();
    }

}
