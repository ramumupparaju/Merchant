package com.incon.connect.ui.settings.update;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityUpdateUserProfileBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.settings.SettingsActivity;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateUserProfileActivity extends BaseActivity implements
        UpDateUserProfileContract.View {
    ActivityUpdateUserProfileBinding binding;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_profile;
    }

    @Override
    protected void initializePresenter() {

    }


    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateUserProfileActivity(this);
        binding.toolbarEditProfile.toolbarTextStore.setVisibility(View.GONE);
        binding.toolbarEditProfile.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backToSettings = new Intent(UpDateUserProfileActivity.this,
                        SettingsActivity.class);
                startActivity(backToSettings);
            }
        });
       binding.toolbarEditProfile.toolbarEditImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.buttonSubmit.setVisibility(View.VISIBLE);
               binding.toolbarEditProfile.toolbarEditImage.setVisibility(View.GONE);

           }
       });


    }
}
