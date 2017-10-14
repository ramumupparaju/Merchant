package com.incon.connect.ui.history.base;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.custom.view.CustomViewPager;
import com.incon.connect.databinding.CustomTabBinding;
import com.incon.connect.databinding.FragmentHistoryTabBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.HistoryTabPagerAdapter;


public abstract class BaseTabFragment extends BaseFragment{

    public abstract void onSearchClickListerner(String searchableText, int searchType);

}