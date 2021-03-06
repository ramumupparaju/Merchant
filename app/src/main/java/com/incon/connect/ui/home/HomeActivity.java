package com.incon.connect.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityHomeBinding;
import com.incon.connect.databinding.ToolBarBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addoffer.fragment.AddOfferMerchantFragment;
import com.incon.connect.ui.buyrequets.BuyRequestFragment;
import com.incon.connect.ui.history.HistoryTabFragment;
import com.incon.connect.ui.home.asignqrcode.fragment.ProductAssignFragment;
import com.incon.connect.ui.notifications.fragment.NotificationsFragment;
import com.incon.connect.ui.scan.ScanTabFragment;
import com.incon.connect.ui.settings.SettingsActivity;
import com.incon.connect.utils.DeviceUtils;
import com.incon.connect.utils.SharedPrefsUtils;
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
    private ToolBarBinding toolBarBinding;

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

        binding.bottomNavigationView.setCurrentItem(TAB_SCAN);


        //changed preference as otp verified
        SharedPrefsUtils.loginProvider().setBooleanPreference(LoginPrefs.IS_REGISTERED, false);
        SharedPrefsUtils.loginProvider().setBooleanPreference(LoginPrefs.IS_FORGOT_PASSWORD, false);

        //hockey app update checking
//        UpdateManager.register(this);
        initializeToolBar();
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);

    }

    public void setToolbarTitle(String title) {
        toolBarBinding.toolbarTitleTv.setText(title);
    }

    protected void initializeToolBar() {
        LayoutInflater layoutInflater = getLayoutInflater();
        toolBarBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.tool_bar, null, false);
        setSupportActionBar(toolBarBinding.toolbar);
        setToolbarTitle(getString(R.string.title_history));

        toolBarBinding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });
        toolBarBinding.toolbarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAssignProductClick();
            }
        });
        replaceToolBar(toolBarBinding.toolbar);

    }

    private FragmentManager.OnBackStackChangedListener backStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    BaseFragment currentFragment = (BaseFragment) getSupportFragmentManager().
                            findFragmentById(R.id.container);
                    currentFragment.setTitle();
                }
            };


    public void onAssignProductClick() {
        //TODO need to change uncomment
        Bundle bundle = new Bundle();
        bundle.putString(BundleConstants.SCANNED_QRCODE, "1-45-1507817691174");
        replaceFragmentAndAddToStack(
                ProductAssignFragment.class, bundle);
        /*Intent intent = new Intent(this, QrcodeBarcodeScanActivity.class);
        startActivityForResult(intent, RequestCodes.PRODUCT_ASSIGN_SCAN);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.PRODUCT_ASSIGN_SCAN:
                    if (data != null) {
                        homePresenter.checkQrCodeValidity(
                                data.getStringExtra(IntentConstants.SCANNED_CODE));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void navigateToProductAssignScreen(String qrCode) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleConstants.SCANNED_QRCODE, qrCode);
        replaceFragmentAndAddToStack(
                ProductAssignFragment.class, bundle);
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
                aClass = AddOfferMerchantFragment.class;
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
