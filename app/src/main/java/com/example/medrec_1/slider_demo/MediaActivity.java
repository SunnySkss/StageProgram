package com.example.medrec_1.slider_demo;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.medrec_1.slider_demo.model.CreateUserResponse;
import com.example.medrec_1.slider_demo.model.DisLikeVedioResponse;
import com.example.medrec_1.slider_demo.model.LikeVedioResponse;
import com.example.medrec_1.slider_demo.model.ViewerResponse;
import com.example.medrec_1.slider_demo.utils.Constant;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    ImageView imgBack,imgLike, imgdislike, imgvedioshare;
    private int position = 0;
    private SeekBar volumeSeekBar;
    private String createDate;

    AudioManager audioManager;// = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    ImageButton volume;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;

    private ExtractorMediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        initializeControlls();

        if (getIntent() != null && getIntent().hasExtra("data")) {
            mData = getIntent().getParcelableExtra("data");
        }

        mobileIp = getIPAddress(true);
        int totlikes = mData.getTotalLike();
        int totdislike = mData.getTotalDislike();
        Date date;
        Date date2;
        String str_date=mData.getCreatedDate();
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            date = (Date) formatter.parse(str_date);
            String dd=formatter.format(date.getTime());
            String pDate=DateFormat.getInstance().format(date);


            String[] parts = pDate.split(" ");
            String strDate = parts[0];
            String strTime=parts[1];
            String strAMPM=parts[2];

            createDate=strDate+" at "+strTime+" "+strAMPM;

        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }

        int viewers=mData.getTotalViews();
        double viewr=(double) viewers/1000;

        likes.setText(String.valueOf(totlikes));
        dislikes.setText(String.valueOf(totdislike));
        vedioTital.setText(mData.getVideoTitle());
        vedioDateTime.setText(createDate);
        vedioDesc.setText(mData.getVideoDescription());
        getViewers();

    }

    public void initializeControlls()
    {
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
        imgBack.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        imgdislike.setOnClickListener(this);
        imgvedioshare.setOnClickListener(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        volumeSeekBar=findViewById(R.id.seekbar);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }


    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(MediaActivity.this, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(MediaActivity.this, R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        ImageButton img=controlView.findViewById(R.id.exo_next);

        img.setSaveEnabled(true);
       // img.clearFocus();
        volume=controlView.findViewById(R.id.mutevol);
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mExoPlayerView.setRewindIncrementMs(1000);
                //mExoPlayerView.getNextFocusForwardId().
                //player.selectTrack(FullPlayer.TYPE_AUDIO, ExoPlayer.TRACK_DISABLED);
                Toast.makeText(MediaActivity.this, "volume key", Toast.LENGTH_SHORT).show();
            }
        });
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);

        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        player.prepare(mediaSource);

        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }


        // mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);
    }


    @Override
    protected void onResume() {

        super.onResume();

        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();

            // String streamUrl ="https://www.youtube.com/watch?v=wJkT-SGOiEQ.m3u8";//
            String streamUrl= Constant.VIDEO_URL+mData.getMediaUrl();
            String userAgent = Util.getUserAgent(MediaActivity.this, getApplicationContext().getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, null, httpDataSourceFactory);
            Uri daUri = Uri.parse(streamUrl);
            mediaSource = new ExtractorMediaSource.Factory(httpDataSourceFactory)
                    .createMediaSource(daUri, null, null);

            // mVideoSource = new  HlsMediaSource(daUri, dataSourceFactory,5,null,null);

//            ImaAdsMediaSource imaAdsMediaSource =
//                    new ImaAdsMediaSource(mediaSource, cacheDataSourceFactory, imaAdsLoader, adUiContainer);
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(MediaActivity.this, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }






















//    @Override
//    protected void onRestoreInstanceState(Bundle outState) {
//        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
//        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
//        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
//
//        super.onSaveInstanceState(outState);
//
//    }

//    @Override
//    public void onSaveInstanceState(Bundle saveInstanceState) {
//        super.onSaveInstanceState(saveInstanceState);
//        saveInstanceState.putInt("Position",videoView.getCurrentPosition());
//        videoView.pause();
//    }

    public void playVideo(String str) {

        MediaController m = new MediaController(this);
        m.setAnchorView(videoView);

//        m.playSoundEffect(View.SOUND_EFFECTS_ENABLED);


        videoView.setMediaController(m);


        Uri myUri = Uri.parse(str);
        videoView.setVideoURI(myUri);
        videoView.start();
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


        Log.d("inside", "retro");


        retrofit2.Call<LikeVedioResponse> call = apiInterface.doLikeVedio(mData.getVideoSourceId(), mobileIp);
        call.enqueue(new Callback<LikeVedioResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LikeVedioResponse> call, Response<LikeVedioResponse> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    onLikeSuccess(response.body());
                }
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
            //int tot_Viewer=mData.getTotalViews();
            int viewers=mData.getTotalViews();
            double viewr=(double) viewers/1000;
            //vedViews.setText(new DecimalFormat("##.#").format( viewr)+" views");
            totViews.setText(new DecimalFormat("##.#").format( viewr)+"k views");
        } else if (body.getMessage().equalsIgnoreCase("Already Added")) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}