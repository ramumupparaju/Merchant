package com.incon.connect.ui.register;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ConnectApplication;
import com.incon.connect.ui.BasePresenter;

public class RegistrationPresenter extends BasePresenter<RegistrationContract.View> implements
        RegistrationContract.Presenter {

    private Context appContext;
    private static final String TAG = RegistrationPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }


}