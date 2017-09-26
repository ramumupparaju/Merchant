package com.incon.connect.ui.history.fragments;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ui.BasePresenter;


/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class PurchasedPresenter extends BasePresenter<PurchasedContract.View> implements
        PurchasedContract.Presenter {

    private static final String TAG = PurchasedPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
    }

}
