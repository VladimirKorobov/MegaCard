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
    public TabPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void Update() {
        for(Fragment fragment:fragments) {
            ((PageFragment)fragment).UpdateView();
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = PageFragment.newInstance(context, position);
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return position == 0 ? "Running" : "Stopped";
    }

}
