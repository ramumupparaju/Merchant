package com.incon.connect.ui.history;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.custom.view.CustomViewPager;
import com.incon.connect.databinding.CustomTabBinding;
import com.incon.connect.databinding.FragmentHistoryTabBinding;
import com.incon.connect.databinding.ToolBarBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.HistoryTabPagerAdapter;
import com.incon.connect.ui.home.HomeActivity;


public class HistoryTabFragment extends BaseFragment {

    private static final String TAG = HistoryTabFragment.class.getSimpleName();
    private FragmentHistoryTabBinding binding;
    private ToolBarBinding toolBarBinding;
    private View rootView;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;
    private HistoryTabPagerAdapter adapter;

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_history_tab, container, false);
            rootView = binding.getRoot();
            initViews();
        }

        return rootView;
    }

    private void initViews() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_history));
        initActionBar();
        initViewPager();
    }


    private void initActionBar() {
    }


    private void initViewPager() {

        defaultTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/OpenSans-Regular.ttf");

        selectedTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/OpenSans-Bold.ttf");

        tabTitles = getResources().getStringArray(R.array.history_tab);


        final CustomViewPager customViewPager = binding.viewPager;
        final TabLayout tabLayout = binding.tabLayout;

        setTabIcons();
        changeTitleFont(0);

        //set up ViewPager
        adapter = new HistoryTabPagerAdapter(
                getFragmentManager(), tabLayout.getTabCount());
        customViewPager.setAdapter(adapter);

        customViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                customViewPager.setCurrentItem(position);
                changeTitleFont(position);
                changeToolbar(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeToolbar(int position) {
        if (toolBarBinding == null) {
            return;
        }
        switch (position) {
            case 0:
                toolBarBinding.toolbarLeftIv.setVisibility(View.VISIBLE);
                toolBarBinding.toolbarRightIv.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 2:
                toolBarBinding.toolbarLeftIv.setVisibility(View.GONE);
                toolBarBinding.toolbarRightIv.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    private void changeTitleFont(int position) {
        for (int i = 0; i < tabTitles.length; i++) {
            View view = binding.tabLayout.getTabAt(i).getCustomView();
            CustomTabBinding customTabView = DataBindingUtil.bind(view);
            customTabView.tabTv.setTypeface(i == position
                    ? selectedTypeFace
                    : defaultTypeFace);
        }
    }

    private void setTabIcons() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabTitles.length; i++) {
            CustomTabBinding customTabView = getCustomTabView();
            customTabView.tabTv.setText(tabTitles[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabView.getRoot()));
        }
    }


    private CustomTabBinding getCustomTabView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_tab, null, false);
    }
}