package com.incon.connect.ui.settings.update;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ConnectApplication;
import com.incon.connect.ui.BasePresenter;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateUserProfilePresenter extends BasePresenter<UpDateUserProfileContract.View>
        implements UpDateUserProfileContract.Presenter {
    private Context appContext;
    private static final String TAG = UpDateUserProfilePresenter.class.getName();


    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }
}
