package com.example.medrec_1.slider_demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int NoTab;

    public PageAdapter(FragmentManager fm,int NoOftab)
    {
        super(fm);
        this.NoTab=NoOftab;
    }
    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0: FragmentTop one=new FragmentTop();
                    return one;
            case 1: FragmentBihar two=new FragmentBihar();
                    return two;
            case 2: FragmentPunjabHaryana three=new FragmentPunjabHaryana();
                    return three;
            case 3: FragmentRajasthan four=new FragmentRajasthan();
                    return four;
            case 4: FragmentOther other=new FragmentOther();
                    return other;

                    default:
                        return null;
        }


    }

    @Override
    public int getCount() {
        return NoTab;
    }
}
