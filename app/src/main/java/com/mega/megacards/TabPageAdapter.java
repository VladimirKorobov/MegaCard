package com.mega.megacards;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    List<Fragment> fragments = new ArrayList<>();
    private Context context;
    ThumbMap thumbMap;
    public TabPageAdapter(FragmentManager fm, Context context, ThumbMap thumbMap) {
        super(fm);
        this.context = context;
        this.thumbMap = thumbMap;
    }

    public void Update() {
        for(Fragment fragment:fragments) {
            ((PageFragment)fragment).UpdateView();
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = PageFragment.newInstance(context, position, this.thumbMap);
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {

        if(position >= 0 && position < thumbMap.keySet().size()) {
            Object[] keys = thumbMap.keySet().toArray();
            return (String)keys[position];
        }
        return "";
    }
}
