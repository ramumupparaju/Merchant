package com.incon.connect.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityHomeBinding;
import com.incon.connect.databinding.ToolBarBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.buyrequets.BuyRequestFragment;
import com.incon.connect.ui.home.fragment.DummyFragment;
import com.incon.connect.ui.history.HistoryTabFragment;
import com.incon.connect.ui.notifications.fragment.NotificationsFragment;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.ui.scan.ScanTabFragment;
import com.incon.connect.ui.settings.SettingsActivity;
import com.incon.connect.utils.DeviceUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.LinkedHashMap;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private static final int TAB_HISTORY = 0;
    private static final int TAB_BUY_REQUESTS_FAVORITES = 1;
    private static final int TAB_SCAN = 2;
    private static final int TAB_OFFERS_STATUS = 3;
    private static final int TAB_NOTIFICATIONS = 4;

    private View rootView;
    private HomePresenter homePresenter;
    private ActivityHomeBinding binding;

    private LinkedHashMap<Integer, Fragment> tabFragments = new LinkedHashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializePresenter() {
        homePresenter = new HomePresenter();
        homePresenter.setView(this);
        setBasePresenter(homePresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);

        rootView = binding.getRoot();
        disableAllAnimation(binding.bottomNavigationView);
        binding.bottomNavigationView.setTextVisibility(true);
        setBottomNavigationViewListeners();
        handleBottomViewOnKeyBoardUp();

        binding.bottomNavigationView.setCurrentItem(TAB_HISTORY);

        //hockey app update checking
//        UpdateManager.register(this);
        initializeToolBar();
    }

    protected void initializeToolBar() {
        LayoutInflater layoutInflater = getLayoutInflater();
        ToolBarBinding toolBarBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.tool_bar, null, false);
        setSupportActionBar(toolBarBinding.toolbar);
        toolBarBinding.toolbarTitleTv.setText(R.string.title_history);

        toolBarBinding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));

            }
        });
        toolBarBinding.toolbarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, QrcodeBarcodeScanActivity.class);
                startActivity(intent);
            }
        });
        replaceToolBar(toolBarBinding.toolbar);
    }

    public void replaceToolBar(View toolBarView) {
        if (toolBarView == null) {
            return;
        }
        binding.containerToolbar.removeAllViews();
        binding.containerToolbar.addView(toolBarView);
    }

    private void setBottomNavigationViewListeners() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setBottomTabStatus(item.getItemId());
                        return true;
                    }
                });
    }

    private void setBottomTabStatus(int selectedItemId) {
        Class<? extends Fragment> aClass = null;
        switch (selectedItemId) {
            case R.id.action_history:
                aClass = HistoryTabFragment.class;
                break;
            case R.id.action_buy_requests_favorites:
                aClass = BuyRequestFragment.class;
                break;
            case R.id.action_scan:
                aClass = ScanTabFragment.class;
                break;
            case R.id.action_offers_status:
                aClass = DummyFragment.class;
                break;
            case R.id.action_notifications:
                aClass = NotificationsFragment.class;
                break;
            default:
                break;
        }

        Fragment tabFragment = tabFragments.get(selectedItemId);
        if (tabFragment == null) {
            Fragment fragment = replaceFragment(aClass, null);
            tabFragments.put(selectedItemId, fragment);
        } else {
            replaceFragment(tabFragment.getClass(), null);
        }
    }

    private void handleBottomViewOnKeyBoardUp() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                        binding.bottomNavigationView.setVisibility(View.VISIBLE);
                        if (heightDiff > DeviceUtils.dpToPx(HomeActivity.this, 200)) {
                            // if more than 200 dp, it's probably a keyboard...
                            binding.bottomNavigationView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void disableAllAnimation(BottomNavigationViewEx bnve) {
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
    }
}
