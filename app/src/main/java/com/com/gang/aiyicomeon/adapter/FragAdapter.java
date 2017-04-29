package com.com.gang.aiyicomeon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gang on 2017/1/22.
 */

public class FragAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    //对于构造函数，这里申请了一个Fragment的List对象，用于保存用于滑动的Fragment对象，并在创造函数中初始化
    public FragAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
