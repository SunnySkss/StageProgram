package com.example.medrec_1.slider_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//second...
public class MainActivity extends AppCompatActivity implements FragmentTop.OnFragmentInteractionListener,
        FragmentBihar.OnFragmentInteractionListener,FragmentPunjabHaryana.OnFragmentInteractionListener,
        FragmentRajasthan.OnFragmentInteractionListener,FragmentOther.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private TabLayout mTablayout;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private Toolbar mtoolbar;
    private NavigationView navigationView;
    private AlertDialog.Builder  alert;
    private ImageView alert_img_contact;
    private Date systime;
    private TextView timesystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTablayout=findViewById(R.id.tablayout);
        mtoolbar=findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        alert = new AlertDialog.Builder(this);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        timesystem=navigationView.getHeaderView(0).findViewById(R.id.time_system_head);
       // alert_img_contact=findViewById(R.id.alert_contact_img);
        systime=new Date();

        SimpleDateFormat formatter= new SimpleDateFormat("hh:mm aa");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        Date date = new Date(System.currentTimeMillis());
        String pDate=DateFormat.getInstance().format(date).toString();
        String dd=formatter.format(date.getTime());
        timesystem.setText(dd);


        navigationView.setNavigationItemSelectedListener(this);
        boolean aa=isNetworkConnected();
        //if(aa) {
            mTablayout.addTab(mTablayout.newTab().setText("TOP"));
            mTablayout.addTab(mTablayout.newTab().setText("BIHAR"));
            mTablayout.addTab(mTablayout.newTab().setText("PUNJAB & HARYANA"));
            mTablayout.addTab(mTablayout.newTab().setText("RAJASTHAN"));
            mTablayout.addTab(mTablayout.newTab().setText("OTHER"));
            mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
            viewPager = (ViewPager) findViewById(R.id.myPager);
            final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), mTablayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
            mTablayout.setSelectedTabIndicatorColor(Color.WHITE);

            ActionBarDrawerToggle toggle;
            toggle = new ActionBarDrawerToggle(this, drawerLayout, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            selectTab();

   }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    private void selectTab() {
        mTablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                mTablayout.setSelectedTabIndicatorColor(Color.WHITE);
                //
                // .mTablayout.setTabTextColors(Color.parseColor("#bbbbbb"), Color.parseColor("#bbbbbb"));


                //viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_aboutus:
                Intent aboutintent=new Intent(this,WebActivity.class);
                aboutintent.putExtra("URL","http://stageprogram.com/About");
                aboutintent.putExtra("name","About Us");
                startActivity(aboutintent);
                        return true;
            case R.id.nav_contact:
                Intent contactintent=new Intent(this,WebActivity.class);
                contactintent.putExtra("URL","http://stageprogram.com/Contact");
                contactintent.putExtra("name","Contact Us");
                startActivity(contactintent);

                        return true;
            case R.id.nav_share:
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String shareMessage= "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                       } catch(Exception e) {
                    }
                return true;
            case R.id.nav_policy:
                Intent policyintent=new Intent(this,WebActivity.class);
                policyintent.putExtra("URL","http://stageprogram.com/Privacy");
                policyintent.putExtra("name","Privacy Policy");
                startActivity(policyintent);

                return true;

        case R.id.nav_exit:
            final AlertDialog.Builder exitbuilder = new AlertDialog.Builder(this);
            exitbuilder.setMessage("Are you sure ...?");
            exitbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "hiiiii", Toast.LENGTH_SHORT).show();
                    exitbuilder.setCancelable(true);

                }
            });
            exitbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Byeee....", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            exitbuilder.create();
            exitbuilder.show();
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START,true);  //Navigation drawer close immediately
        return true;
    }
}
