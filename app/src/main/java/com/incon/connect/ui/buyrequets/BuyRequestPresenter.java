package com.incon.connect.ui.buyrequets;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ui.BasePresenter;


/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class BuyRequestPresenter extends BasePresenter<BuyRequestContract.View> implements
        BuyRequestContract.Presenter {

    private static final String TAG = BuyRequestPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
    }

}
