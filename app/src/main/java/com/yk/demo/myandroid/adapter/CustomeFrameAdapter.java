package com.yk.demo.myandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Frame adater
 * @author yk
 * @version V1.0.0
 * created at 2017/1/18 12:00
 */

public class CustomeFrameAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mData;
    private ArrayList<String> mTitle;

    public CustomeFrameAdapter(FragmentManager fm, ArrayList<Fragment> mData, ArrayList<String> mTitle) {
        super(fm);
        this.mData = mData;
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
