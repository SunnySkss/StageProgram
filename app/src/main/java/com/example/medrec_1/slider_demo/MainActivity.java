package com.example.medrec_1.slider_demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.stream.DoubleStream;
//ableLayout;

public class MainActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener,FragmentTwo.OnFragmentInteractionListener,FragmentThree.OnFragmentInteractionListener,FragmentRajasthan.OnFragmentInteractionListener,FragmentOther.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    TabLayout mTablayout;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    Toolbar mtoolbar;
    NavigationView navigationView;
    AlertDialog.Builder  alert;
    Menu mymenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTablayout=findViewById(R.id.tablayout);
        mtoolbar=findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        //mymenu=(Menu)findViewById(R.id.nav_menu);
        alert = new AlertDialog.Builder(this);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mTablayout.addTab(mTablayout.newTab().setText("TOP") );
        mTablayout.addTab(mTablayout.newTab().setText("BIHAR") );
        mTablayout.addTab(mTablayout.newTab().setText("PUNJAB & HARYANA") );
        mTablayout.addTab(mTablayout.newTab().setText("RAJASTHAN") );
        mTablayout.addTab(mTablayout.newTab().setText("OTHER") );
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager=(ViewPager)findViewById(R.id.myPager);
        final PageAdapter adapter=new PageAdapter(getSupportFragmentManager(),mTablayout.getTabCount());
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
                Toast.makeText(this, "You click on about us", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);  //Navigation drawer close immediately
                return true;
            case R.id.nav_contact:

                AlertDialog builder=alert.create();//
                builder.setMessage("You cantact to us below No:-\n +9185858585");
                //builder.setCancelable(true);
                builder.setTitle("hiii");
                builder.show();
                Toast.makeText(this, "You click on Contact....", Toast.LENGTH_SHORT).show();
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);  //Navigation drawer close immediately
        return true;
    }
}
