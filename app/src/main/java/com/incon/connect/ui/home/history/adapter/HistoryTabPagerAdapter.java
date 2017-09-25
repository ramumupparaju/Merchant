package com.incon.connect.ui.home.history.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.incon.connect.ui.home.fragment.DummyFragment;
import com.incon.connect.ui.home.history.fragments.PurchasedFragment;

import java.util.HashMap;

public class HistoryTabPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public HistoryTabPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tabFragment = fragmentHashMap.get(position);
        if (tabFragment != null) {
            return tabFragment;
        }

        switch (position) {
            case 0:
                tabFragment = new PurchasedFragment();
                break;
            case 1:
                tabFragment = new DummyFragment();
                break;
            case 2:
                tabFragment = new DummyFragment();
                break;
            default:
                break;
        }
        fragmentHashMap.put(position, tabFragment);
        return tabFragment;
    }

    public Fragment getFragmentFromPosition(int position) {
        return fragmentHashMap.get(position);
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}