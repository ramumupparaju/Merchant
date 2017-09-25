package com.incon.connect.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.incon.connect.R;
import com.incon.connect.databinding.ActivityHomeBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.scaning.Qrandbarcodescanner;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistration;


/**
 * Created by PC on 9/25/2017.
 */

public class HomeActivity extends BaseActivity {
    ActivityHomeBinding activityHomeBinding;
    Intent i;
    TextView tMobilenumber;
    ImageView imScan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializePresenter() {

    }


    public void onScanningClick() {
        startActivity(new Intent(HomeActivity.this , Qrandbarcodescanner.class));

    }

    public void onTextClick() {
        startActivity(new Intent(HomeActivity.this , WarrantyRegistration.class));


    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityHomeBinding = DataBindingUtil.setContentView(this, getLayoutId());
        activityHomeBinding.setScaning(this);
        activityHomeBinding.setMobilenumber(this);
       // imScan = (ImageView) findViewById(R.id.im_scann);
       // tMobilenumber = (TextView) findViewById(R.id.text_mobilenumber);

    }


}
