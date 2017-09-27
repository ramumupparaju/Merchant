package com.incon.connect.ui.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityRegistrationBinding;
import com.incon.connect.databinding.ViewRegistrationBottomButtonsBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.register.adapter.RegistrationPagerAdapter;
import com.incon.connect.ui.register.fragment.RegistrationStoreFragment;
import com.incon.connect.ui.register.fragment.RegistrationUserFragment;


public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    private View rootView;
    public RegistrationPresenter registrationPresenter;
    private ActivityRegistrationBinding binding;
    private ViewRegistrationBottomButtonsBinding buttonsBinding;
    private RegistrationPagerAdapter registrationPagerAdapter;
    private int currentPagePos;
    private View.OnClickListener buttonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_left:
                    navigateToBack();
                    break;
                case R.id.button_right:
                    Fragment currentRegistionFragment = registrationPagerAdapter.getItem(
                            binding.viewpagerRegister.getCurrentItem());
                    if (currentRegistionFragment instanceof RegistrationUserFragment) {
                        ((RegistrationUserFragment) currentRegistionFragment).onClickNext();
                    } else if (currentRegistionFragment instanceof RegistrationStoreFragment) {
                        ((RegistrationStoreFragment) currentRegistionFragment).onClickNext();
                    }
                    break;
                default:
                    //do nothing
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initializePresenter() {
        registrationPresenter = new RegistrationPresenter();
        registrationPresenter.setView(this);
        setBasePresenter(registrationPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        buttonsBinding = binding.includeRegisterBottomButtons;
        rootView = binding.getRoot();

        buttonsBinding.buttonLeft.setOnClickListener(buttonClickListner);
        buttonsBinding.buttonLeft.setText(getString(R.string.action_back));
        buttonsBinding.buttonRight.setOnClickListener(buttonClickListner);
        buttonsBinding.buttonRight.setText(getString(R.string.action_next));
        initializePagerAdapter();
    }

    // Instantiate a PagerAdapter and sets to viewpager.
    private void initializePagerAdapter() {
        registrationPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager());
        binding.viewpagerRegister.setAdapter(registrationPagerAdapter);
        binding.viewpagerRegister.addOnPageChangeListener(registerOnPageChangeListener);
        registerOnPageChangeListener.onPageSelected(0);
    }


    @Override
    public void navigateToNext() {
        currentPagePos = binding.viewpagerRegister.getCurrentItem();
        if (currentPagePos < binding.viewpagerRegister.getChildCount()) {
            binding.viewpagerRegister.setCurrentItem(++currentPagePos);
        }
    }

    @Override
    public void navigateToBack() {
        currentPagePos = binding.viewpagerRegister.getCurrentItem();
        if (currentPagePos > 0) {
            binding.viewpagerRegister.setCurrentItem(--currentPagePos);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        navigateToBack();
    }

    private ViewPager.OnPageChangeListener registerOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position,
                                   float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            /*PageListener fragment = (PageListener) registrationPagerAdapter
                    .getItem(position);
            fragment.setViewPagerListener(RegistrationActivity.this);
            fragment.onPageDisplayed(binding.includeRegisterBottomButtons, binding);*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        registerPresenter.disposeAll();
    }

}