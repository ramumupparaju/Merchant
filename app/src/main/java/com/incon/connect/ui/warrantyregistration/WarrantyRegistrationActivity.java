package com.incon.connect.ui.warrantyregistration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseActivity;

/**
 * Created by PC on 9/25/2017.
 */

public class WarrantyRegistrationActivity extends BaseActivity implements
        WarrantRegistrationContract.View {
    WarrantRegistrationPresenter warrantRegistrationPresenter;
    private ListView lv;
    ActivityWarrantyRegistrationBinding activityWarrantyRegistrationBinding;
    LinearLayout productDetailsLayout;
    ArrayAdapter<String> adapter;
    String products[] = {"Samsung", "Redmi", "Moto"};
    @Override
    protected int getLayoutId() {
        return  R.layout.activity_warranty_registration;
    }

    @Override
    protected void initializePresenter() {

    }
    public void onFloatingClick() {
        Toast.makeText(getApplicationContext(), "Floating button click", Toast.LENGTH_LONG).show();


    }

    public void onSubmitClick() {
        Toast.makeText(getApplicationContext(), "submit button clicked", Toast.LENGTH_LONG).show();


    }


    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityWarrantyRegistrationBinding = DataBindingUtil.setContentView(this, getLayoutId());
        activityWarrantyRegistrationBinding.setWarrantyregistration(this);

    }
}

