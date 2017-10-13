package com.incon.connect.ui.settings.update;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityUpdateStoreProfileBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.settings.SettingsActivity;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateStoreProfileActivity extends BaseActivity implements
        UpDateStoreProfileContract {
    ActivityUpdateStoreProfileBinding binding;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_store_profile;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateStoreProfile(this);
        binding.toolbarEditProfile.toolbarTextStore.setVisibility(android.view.View.VISIBLE);
        binding.toolbarEditProfile.toolbarTextUser.setVisibility(android.view.View.GONE);
        binding.toolbarEditProfile.toolbarLeftIv.setOnClickListener(
                new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent backToSettings = new Intent(UpDateStoreProfileActivity.this,
                        SettingsActivity.class);
                startActivity(backToSettings);
            }
        });
        binding.toolbarEditProfile.toolbarEditImage.setOnClickListener(
                new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                binding.buttonSubmit.setVisibility(android.view.View.VISIBLE);
                binding.toolbarEditProfile.toolbarEditImage.setVisibility(android.view.View.GONE);

            }
        });

    }
}
