package com.incon.connect.ui.addoffer.fragment;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ConnectApplication;
import com.incon.connect.ui.BasePresenter;

/**
 * Created by PC on 10/8/2017.
 */

public class AddOfferMerchantPresenter extends BasePresenter<AddOfferMerchantContract.View>
        implements AddOfferMerchantContract.Presenter {
    private static final String TAG = AddOfferMerchantPresenter.class.getName();
    private Context appContext;
    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }
}
