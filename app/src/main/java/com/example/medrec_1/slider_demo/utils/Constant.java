package com.example.medrec_1.slider_demo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Constant extends AppCompatActivity {

    public static final String VIDEO_URL="http://stageprogram.com/";
    static Context con;

    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null ;
        }catch (Exception e){

        }
        return false;
    }

    public static boolean isWifiConnected()
    {
        try {
            ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            //if(cm.getActiveNetwork().)
            NetworkInfo mWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWifi.isConnected())
                return true;
        }catch (Exception e){
            Log.e("msg",e.getMessage());

        }
//        return cm.getActiveNetworkInfo(ConnectivityManager.TYPE_WIFI) != null;
        return false;
    }
}
