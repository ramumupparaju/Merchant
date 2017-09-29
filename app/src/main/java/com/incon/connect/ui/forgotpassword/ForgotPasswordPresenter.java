package com.incon.connect.ui.forgotpassword;

import android.content.Context;
import android.os.Bundle;

import com.incon.connect.ConnectApplication;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.registration.ValidateOtpResponse;
import com.incon.connect.ui.BasePresenter;

import java.util.HashMap;

import io.reactivex.Observable;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.View> implements
        ForgotPasswordContract.Presenter {

    private Context appContext;
    private static final String TAG = ForgotPasswordPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void forgotPassword(HashMap<String, String> email) {
        /*getView().showProgress(appContext.getString(R.string.progress_forgotpassword));
        Observable<ValidateOtpResponse> forgotPasswordObserver = getForgotPasswordObserver(email);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().navigateToResetPromtPage();
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().showErrorMessage(ErrorMsgUtil.getErrorMsg(e));
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        forgotPasswordObserver.subscribe(observer);
        addDisposable(observer);*/
        getView().navigateToResetPromtPage();
    }

    public Observable<ValidateOtpResponse> getForgotPasswordObserver(
            HashMap<String, String> email) {
        return AppApiService.getInstance().forgotPassword(email);
    }

}
