package com.example.medrec_1.slider_demo;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.medrec_1.slider_demo.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvideo);
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
        imgBack.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        imgdislike.setOnClickListener(this);
        imgvedioshare.setOnClickListener(this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (getIntent() != null && getIntent().hasExtra("data")) {
            mData = getIntent().getParcelableExtra("data");
        }
        Intent i = getIntent();
        //String videoUrl = i.getStringExtra("mediaUrl");
        //int totlikes = i.getIntExtra("likesVed", 0);
        //int totdislike = i.getIntExtra("dislikeVed", 1);

//        Utils.getIPAddress(true);
        mobileIp = getIPAddress(true);

        // String videoUrl = Constant.VIDEO_URL +mData.getMediaUrl();
        int totlikes = mData.getTotalLike();
        int totdislike = mData.getTotalDislike();

        final String dateTime = mData.getCreatedDate();

        // ZonedDateTime d = ZonedDateTime.parse(dateTime);

    /*    DateTimeFormatter formatter = null;
        try {
            formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd'T'hh:mm:ss");
        } catch (Exception e) {

            e.printStackTrace();
        }

        final ZonedDateTime parsed = ZonedDateTime.parse(dateTime,formatter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));


*/

        String datenTime = mData.getCreatedDate();
        String[] parts = datenTime.split("T");
        String part1 = parts[0];
        String part2 = parts[1];
        // final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        likes.setText(String.valueOf(totlikes));
        dislikes.setText(String.valueOf(totdislike));
        vedioTital.setText(mData.getVideoTitle());
        totViews.setText(String.valueOf(mData.getTotalViews()) + "views");
        vedioDateTime.setText(mData.getCreatedDate());
        vedioDesc.setText(mData.getVideoDescription());
        getViewers();
        playVideo(Constant.VIDEO_URL + mData.getMediaUrl());


    }

    public void playVideo(String str) {
        MediaController m = new MediaController(this);
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
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
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
//                Intent share = new Intent(Intent.ACTION_SEND);
//
//                // If you want to share a png image only, you can do:
//                // setType("image/png"); OR for jpeg: setType("image/jpeg");
//                share.setType("image/*");
//
//                // Make sure you put example png image named myImage.png in your
//                // directory
//                String imagePath = Constant.VIDEO_URL+mData.getMainThumbnailUrl();
//
//                File imageFileToShare = new File(imagePath);
//
//                Uri uri = Uri.fromFile(imageFileToShare);
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//
//                startActivity(Intent.createChooser(share, "Share Image!"));
              //  Bitmap bitmap=getB
    Bitmap bitmap=getBitmapFormVIew(videoView);
//    try
//    {
//       // File file=new File(Constant.VIDEO_URL+mData.getMainThumbnailUrl());
//      //  FileOutputStream fOut=new FileOutputStream(file);
////       // bitmap.compress(Bitmap.CompressFormat.JPEG,100,fOut);
////        fOut.flush();
////        fOut.close();
////        file.setReadable(true,false);
//        final Intent intent=new Intent(Intent.ACTION_SEND);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Intent.EXTRA_TEXT,Uri.parse(Constant.VIDEO_URL+mData.getMainThumbnailUrl()));
//        intent.setType("image/png");
//        startActivity(Intent.createChooser(intent,"share image via"));
//
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }


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

//                Uri imageUri = Uri.parse(Constant.VIDEO_URL+mData.getMediaUrl());
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                //Target whatsapp:
//
//                shareIntent.setPackage("com.whatsapp");
//                //Add text and then Image URI
//                shareIntent.putExtra(Intent.EXTRA_TEXT, mData.getVideoTitle());
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                shareIntent.setType("image/jpeg");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                try {
//                    startActivity(shareIntent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
//                }
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