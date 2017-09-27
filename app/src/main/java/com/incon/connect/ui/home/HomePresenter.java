package com.incon.connect.ui.home;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ui.BasePresenter;


/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements
        HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
    }

}
