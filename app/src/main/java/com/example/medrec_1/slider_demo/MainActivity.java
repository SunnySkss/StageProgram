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
//ableLayout;

public class MainActivity extends AppCompatActivity implements FragmentTop.OnFragmentInteractionListener,
        FragmentBihar.OnFragmentInteractionListener,FragmentPunjabHaryana.OnFragmentInteractionListener,
        FragmentRajasthan.OnFragmentInteractionListener,FragmentOther.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    TabLayout mTablayout;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    Toolbar mtoolbar;
    NavigationView navigationView;
    AlertDialog.Builder  alert;
    ImageView alert_img_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTablayout=findViewById(R.id.tablayout);
        mtoolbar=findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        alert = new AlertDialog.Builder(this);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
       // alert_img_contact=findViewById(R.id.alert_contact_img);

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
            mTablayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

            ActionBarDrawerToggle toggle;
            toggle = new ActionBarDrawerToggle(this, drawerLayout, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            selectTab();

    /*    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                for (int i = 0; i < mTablayout.getTabCount(); ++i)
                    mTablayout.getTabAt(i).setIcon(i != position ? TAB_ICONS_UNSELECTED[i] : TAB_ICONS_SELECTED[i]);
            }
        });*/
//        }
//        else
//            Toast.makeText(this, "Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
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
                mTablayout.setTabTextColors(Color.parseColor("#726272"), Color.parseColor("#ffffff"));


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
                startActivity(aboutintent);
                      //  Toast.makeText(this, "You click on about us", Toast.LENGTH_SHORT).show();
                       // drawerLayout.closeDrawer(GravityCompat.START);  //Navigation drawer close immediately
                        return true;
            case R.id.nav_contact:
                Intent contactintent=new Intent(this,WebActivity.class);
                contactintent.putExtra("URL","http://stageprogram.com/Contact");
                startActivity(contactintent);
                   // startActivity(new Intent(MainActivity.this,ContactActivity.class));
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                LayoutInflater layoutInflaterContact=this.getLayoutInflater();
//                View dialogviewContact=layoutInflaterContact.inflate(R.layout.alertcontact,null);
//                alert.setView(dialogviewContact);

             // alert_img_contact.setImageResource(R.drawable.office);
               // alert.setView(dialogviewContact);
//                alert.setNeutralButton("Ok!!", new DialogInterface.OnClickListener() {
//                   public void onClick(DialogInterface dlg, int sumthin) {
//
//                   }
//                });
//                alert.create();
//                alert.show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setMessage("You can cantact to us below No:-\n +911234567890");
//                        builder.setPositiveButton("Ok",null);
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "hiiiii", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        builder.setIcon(R.drawable.ic_info);
//                        builder.setTitle("Contact Info");
//                        builder.create();
//                        builder.show();
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
                        //e.toString();
                    }
                 //   drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_policy:
                Intent policyintent=new Intent(this,WebActivity.class);
                policyintent.putExtra("URL","http://stageprogram.com/Privacy");
                startActivity(policyintent);
//                startActivity(new Intent(MainActivity.this,PolicyActivity.class));

//                final AlertDialog.Builder myalert=new AlertDialog.Builder(this);
//                LayoutInflater layoutInflater=this.getLayoutInflater();
//                View dialogview=layoutInflater.inflate(R.layout.alert_layout,null);
//                myalert.setView(dialogview);
//                TextView texTitle=dialogview.findViewById(R.id.textTitle);
//                TextView textMsg=dialogview.findViewById(R.id.textMsg);
//                Button btnok=dialogview.findViewById(R.id.okBtn);
//                texTitle.setText("Welcome To India");
//                textMsg.setText("Are you a student....?");
//                final AlertDialog alertDialog = myalert.create();
//                alertDialog.show();
//                btnok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "Data Saved...", Toast.LENGTH_SHORT).show();
//                        alertDialog.dismiss();
//                        return ;
//                    }
//                });
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
// jjhiojoijijijjoi