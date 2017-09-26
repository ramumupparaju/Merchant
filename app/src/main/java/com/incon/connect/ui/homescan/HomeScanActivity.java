package com.incon.connect.ui.homescan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityHomeScanBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.scaning.Qrandbarcodescanner;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistration;


/**
 * Created by PC on 9/25/2017.
 */

public class HomeScanActivity extends BaseActivity {
    ActivityHomeScanBinding activityHomeScanBinding;
    Intent i;
    TextView tMobilenumber;
    ImageView imScan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_scan;
    }

    @Override
    protected void initializePresenter() {

    }


    public void onScanningClick() {
        startActivity(new Intent(HomeScanActivity.this , Qrandbarcodescanner.class));

    }

    public void onTextClick() {
        startActivity(new Intent(HomeScanActivity.this , WarrantyRegistration.class));


    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityHomeScanBinding = DataBindingUtil.setContentView(this, getLayoutId());
       activityHomeScanBinding.setMobilenumber(this);
        activityHomeScanBinding.setScanning(this);
       // imScan = (ImageView) findViewById(R.id.im_scann);
       // tMobilenumber = (TextView) findViewById(R.id.text_mobilenumber);

    }


}
