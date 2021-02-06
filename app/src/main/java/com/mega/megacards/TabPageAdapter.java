package com.mega.megacards;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPageAdapter extends FragmentStatePagerAdapter {
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
        Fragment fragment;
        if(position >= fragments.size()) {
            fragment = PageFragment.newInstance(context, position, this.thumbMap);
            fragments.add(fragment);
        }

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return thumbMap.keySet().size();
    }
    @Override
    public CharSequence getPageTitle(int position) {

        if(position >= 0 && position < thumbMap.keySet().size()) {
            Object[] keys = thumbMap.keySet().toArray();
            return (String)keys[position];
        }
        return "";
    }
    @Override
    public int getItemPosition(Object object) {
        return FragmentStatePagerAdapter.POSITION_NONE;
    }
}
