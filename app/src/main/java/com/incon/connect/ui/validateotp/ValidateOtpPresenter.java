package com.incon.connect.ui.validateotp;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

/**
 * Created on 08 Jun 2017 8:31 PM.
 */
public class ValidateOtpPresenter extends
        BasePresenter<ValidateOtpContract.View> implements
        ValidateOtpContract.Presenter {

    private Context appContext;
    private static final String TAG = ValidateOtpPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void validateOTP(HashMap<String, String> verify) {
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                getView().hideProgress();
                getView().validateOTP(loginResponse);
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        AppApiService.getInstance().validateOtp(verify).subscribe(observer);
        addDisposable(observer);
    }
}
