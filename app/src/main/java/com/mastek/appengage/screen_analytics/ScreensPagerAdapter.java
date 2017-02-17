package com.mastek.appengage.screen_analytics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Pravin103082 on 14-02-2017.
 */

public class ScreensPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public ScreensPagerAdapter(FragmentManager fm ,List<Fragment> fragmentList) {
        super(fm);
        fragments=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


   /* @Override
    public float getPageWidth(int position) {
         return 0.93f;
    }*/
}
