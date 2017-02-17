package com.mastek.appengage.device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Pravin103082 on 06-02-2017.
 */

public class DevicesPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    List<String> tabs;
    public DevicesPagerAdapter(FragmentManager fm ,List<Fragment> fragmentList, List<String> tabs) {
        super(fm);
        fragments=fragmentList;
        this.tabs=tabs;

    }


    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
