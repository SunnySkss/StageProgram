package com.example.medrec_1.slider_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.medrec_1.slider_demo.utils.Constant;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Callback;
import retrofit2.Response;

public class MediaActivity extends AppCompatActivity implements View.OnClickListener {

    String mobileIp;
   // private ArrayList<LikeVedioResponse> likeVedioResponses = new ArrayList<>();
    APIInterface apiInterface;
    VideoView videoView;
    TextView likes, dislikes, vedioTital, totViews, vedioDateTime, vedioDesc;
    CreateUserResponse mData;
    ImageView imgBack, imgdislike, imgvedioshare;
    ImageButton imgLike;
    private int position = 0;
    private SeekBar volumeSeekBar;
    private String createDate;

    private int measuredWidth = 0;
    private int measuredHeight = 0;
    String videoUrl="";
    private  PlayerView playerView;
    private  SimpleExoPlayer exoPlayer;
    //private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }
    private ProgressBar progressBar;
    AudioManager audioManager;// = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    ImageButton fullscreenbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);
        fullscreenbtn=findViewById(R.id.fullscreen);
        videoView = findViewById(R.id.myVedionew);
        likes = findViewById(R.id.likesVedio);
        dislikes = findViewById(R.id.dislikeVedio);
        vedioTital = findViewById(R.id.vedioTital);
        totViews = findViewById(R.id.TotViews);
        vedioDateTime = findViewById(R.id.vedioDateTime);
        vedioDesc = findViewById(R.id.vedioDesc);
        imgLike = findViewById(R.id.like_vedio_img);
        imgdislike = findViewById(R.id.dislike_vedio_img);
        imgvedioshare = findViewById(R.id.share_vedio_img);
        imgBack=findViewById(R.id.imgback);
        progressBar=findViewById(R.id.progress_bar);




        fullscreenbtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
              //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                android.widget.RelativeLayout.LayoutParams params = new android.widget.RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
               // videoView.setNewDimension(metrics.widthPixels, metrics.heightPixels);
//                player.getHolder().setFixedSize(metrics.heightPixels,
//                        metrics.widthPixels);
                videoView.setLayoutParams(params);
            }

        });


//        progressDialog = new ProgressDialog(MediaActivity.this);
//        progressDialog.setTitle("welcome  to my vedio");
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(true);
        // show the progress bar
//        progressDialog.show();
        imgBack.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        imgdislike.setOnClickListener(this);
        imgvedioshare.setOnClickListener(this);

        //playerView=findViewById(R.id.myVedionew);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        volumeSeekBar=findViewById(R.id.seekbar);
        if (getIntent() != null && getIntent().hasExtra("data")) {
            mData = getIntent().getParcelableExtra("data");
        }

        mobileIp = getIPAddress(true);
        videoUrl = Constant.VIDEO_URL +mData.getMediaUrl();
        int totlikes = mData.getTotalLike();
        int totdislike = mData.getTotalDislike();
               //   2017-05-18T21:01:55.203

         //ZonedDateTime d = ZonedDateTime.parse(dateTime);
        Date date;
        String str_date=mData.getCreatedDate();
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            date = (Date) formatter.parse(str_date);

            String pDate=DateFormat.getInstance().format(date).toString();
            createDate=DateFormat.getInstance().format(date).toString();
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }




        likes.setText(String.valueOf(totlikes));
        dislikes.setText(String.valueOf(totdislike));
        vedioTital.setText(mData.getVideoTitle());
        totViews.setText(String.valueOf(mData.getTotalViews()) + "views");
        vedioDateTime.setText(createDate);
        vedioDesc.setText(mData.getVideoDescription());
        getViewers();


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
               // progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    videoView.pause();
                }
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        },2000);

       // playVideo(Constant.VIDEO_URL + mData.getMediaUrl());

//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                audioManager.setStreamVolume(exoPlayer.getAudioStreamType(), i, 0);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

    }

    public void setNewDimension(int width, int height) {
        this.measuredHeight = height;
        this.measuredWidth = width;
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position=savedInstanceState.getInt("Position");
        videoView.seekTo(position);

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("Position",videoView.getCurrentPosition());
        videoView.pause();
    }

    public void playVideo(String str) {

        //progressBar.setVisibility(View.GONE);
        MediaController mcontroller =  (MediaController) findViewById(R.id.mymediaController);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.BOTTOM;
//        mcontroller.setLayoutParams(lp);


         mcontroller.setAnchorView(videoView);

//        m.playSoundEffect(View.SOUND_EFFECTS_ENABLED);

        videoView.setMediaController(mcontroller);

        Uri myUri = Uri.parse(str);
        videoView.setVideoURI(myUri);
        videoView.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        MediaController mcontroller =new MediaController  (this);//MediaController) findViewById(R.id.mymediaController);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.BOTTOM;
//        mcontroller.setLayoutParams(lp);

//
        mcontroller.setAnchorView(videoView);

//        m.playSoundEffect(View.SOUND_EFFECTS_ENABLED);

        videoView.setMediaController(mcontroller);

        Uri myUri = Uri.parse(videoUrl);
        videoView.setVideoURI(myUri);
        videoView.start();








        //progressBar.setVisibility(View.GONE);
//        int progress = 15 * 100 / 100;
//        progressBar.setProgress(progress);


//        exoPlayer=ExoPlayerFactory.newSimpleInstance(this,
//                new DefaultTrackSelector());
//        playerView.setPlayer(exoPlayer);
//
//        DefaultDataSourceFactory dataSourceFactory=new DefaultDataSourceFactory(this,
//                Util.getUserAgent(this,"eo"));
//        ExtractorMediaSource mediaSource=new ExtractorMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(videoUrl));
//        exoPlayer.setVolume(12);
//        exoPlayer.prepare(mediaSource);
//        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_vedio_img:
                LikeVedioAPI();
                break;
            case R.id.dislike_vedio_img:
                DislikeVedioAPI();
                break;
            case R.id.share_vedio_img:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String path=Constant.VIDEO_URL+mData.getMainThumbnailUrl();
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
//                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareMessage=shareMessage+Constant.VIDEO_URL+mData.getMediaUrl();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
            case R.id.imgback:
                onBackPressed();
                //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public Bitmap getBitmapFormVIew(View view)
    {
        Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnBitmap);
        Drawable bgDrawable=view.getBackground();
        if(bgDrawable!=null)
        {
            bgDrawable.draw(canvas);
        } else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnBitmap;
    }
    public void LikeVedioAPI() {

        // userList.clear();
        Log.d("inside", "retro");
        // Call<ResponseBody> call2=apiInterface.doGetListResources();

        retrofit2.Call<LikeVedioResponse> call = apiInterface.doLikeVedio(mData.getVideoSourceId(), mobileIp);
        call.enqueue(new Callback<LikeVedioResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LikeVedioResponse> call, Response<LikeVedioResponse> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    //for (int i = 0; i < response.body().size(); i++) {

                    onLikeSuccess(response.body());
                    // likeVedioResponses.add(response.body());//.get(i));
                }
                //setAdapter(createUserResponses);
            }

            @Override
            public void onFailure(retrofit2.Call<LikeVedioResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                call.cancel();
            }

        });
    }

    private void onLikeSuccess(LikeVedioResponse body) {
        if (body != null && body.getMessage().equalsIgnoreCase("Added")) {
            int Lik=mData.getTotalLike();
            likes.setText(String.valueOf(Lik+1));
            Toast.makeText(this, "Like the video", Toast.LENGTH_SHORT).show();

        } else if (body.getMessage().equalsIgnoreCase("Already Added")) {
            Toast.makeText(this, "You already like this video", Toast.LENGTH_SHORT).show();
        }

    }


    public void DislikeVedioAPI()
    {
        retrofit2.Call<DisLikeVedioResponse> call=apiInterface.doDisLikeVedio(mData.getVideoSourceId(),mobileIp);
        call.enqueue(new Callback<DisLikeVedioResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DisLikeVedioResponse> call, Response<DisLikeVedioResponse> response) {
                if(response.isSuccessful() && response.code()==200)
                {
                    onDisLike(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<DisLikeVedioResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                call.cancel();
            }
        });
    }

    private void onDisLike(DisLikeVedioResponse body) {
        if(body!=null && body.getMessage().equalsIgnoreCase("Added")){
            int DisL=mData.getTotalDislike();
            dislikes.setText(String.valueOf(DisL+1));
            Toast.makeText(this, "Dislike the video", Toast.LENGTH_SHORT).show();
        } else if (body.getMessage().equalsIgnoreCase("Already Added")) {
            Toast.makeText(this, "You already Dislike this video", Toast.LENGTH_SHORT).show();
        }

    }

    public void getViewers()
    {
        retrofit2.Call<ViewerResponse>call=apiInterface.doViewers(mData.getVideoSourceId(),mobileIp);
        call.enqueue(new Callback<ViewerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ViewerResponse> call, Response<ViewerResponse> response) {
                if(response.isSuccessful() && response.code()==200)
                {
                        onViewers(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ViewerResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                call.cancel();
            }
        });
    }

    private void onViewers(ViewerResponse body) {
        if(body!=null && body.getMessage().equalsIgnoreCase("Incremented")){
            int tot_Viewer=mData.getTotalViews();
            totViews.setText(String.valueOf(tot_Viewer+1)+" views");
           // Toast.makeText(this, "Dislike the video", Toast.LENGTH_SHORT).show();
        } else if (body.getMessage().equalsIgnoreCase("Already Added")) {
           // Toast.makeText(this, "You already Dislike this video", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}