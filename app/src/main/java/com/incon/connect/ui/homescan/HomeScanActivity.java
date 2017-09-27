package com.incon.connect.ui.homescan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityHomeScanBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.scaning.QrandbarcodescannerActivity;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistrationActivity;


/**
 * Created by PC on 9/25/2017.
 */

public class HomeScanActivity extends BaseActivity implements HomeScanContract.View {
    ActivityHomeScanBinding activityHomeScanBinding;
    HomeScanPresenter homeScanPresenter;
   static Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_scan;
    }

    @Override
    protected void initializePresenter() {

    }


    public void onScanningClick() {
        intent = new Intent(HomeScanActivity.this , QrandbarcodescannerActivity.class);
        startActivity(intent);

    }

    public  void onTextClick() {
        intent = new Intent(HomeScanActivity.this , WarrantyRegistrationActivity.class);
        startActivity(intent);


    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityHomeScanBinding = DataBindingUtil.setContentView(this, getLayoutId());
        activityHomeScanBinding.setHomescanactivity(this);

    }


}
