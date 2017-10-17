package com.incon.connect.ui.history;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.custom.view.CustomViewPager;
import com.incon.connect.custom.view.FilterBySearchDialog;
import com.incon.connect.databinding.CustomTabBinding;
import com.incon.connect.databinding.FragmentHistoryTabBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.HistoryTabPagerAdapter;
import com.incon.connect.ui.history.base.BaseTabFragment;
import com.incon.connect.ui.home.HomeActivity;


public class HistoryTabFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = HistoryTabFragment.class.getSimpleName();
    private FragmentHistoryTabBinding binding;
    private View rootView;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;
    private HistoryTabPagerAdapter adapter;
    private FilterBySearchDialog filterBySearch;
    private int filterType;

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
        initViewPager();
        binding.searchLayout.searchIconIv.setOnClickListener(this);
        binding.searchLayout.filterIconIv.setOnClickListener(this);
    }

    private void initViewPager() {

        AssetManager assets = getActivity().getAssets();
        defaultTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");

        selectedTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Bold.ttf");

        tabTitles = getResources().getStringArray(R.array.history_tab);


        final CustomViewPager customViewPager = binding.viewPager;
        final TabLayout tabLayout = binding.tabLayout;

        setTabIcons();
        changeTitleFont(0);

        //set up ViewPager
        adapter = new HistoryTabPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        customViewPager.setAdapter(adapter);

        customViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                customViewPager.setCurrentItem(position);
                changeTitleFont(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon_iv:
                int currentItem = binding.viewPager.getCurrentItem();
                BaseTabFragment fragmentFromPosition = (BaseTabFragment) adapter.
                        getFragmentFromPosition(currentItem);
                String searchableText = binding.searchLayout.searchEt.getText().toString();
                if (TextUtils.isEmpty(searchableText)) {
                    filterType = FilterConstants.NONE;
                }
                fragmentFromPosition.onSearchClickListerner(searchableText, filterType);
                break;
            case R.id.filter_icon_iv:
                showFilterOptionsDialog();
                break;
            default:
                //do nothing
        }
    }

    private void showFilterOptionsDialog() {
        filterBySearch = new FilterBySearchDialog(getActivity(), iClickCallback);
        filterBySearch.initDialogLayout();
    }

    IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            filterType = position;
        }
    };
}