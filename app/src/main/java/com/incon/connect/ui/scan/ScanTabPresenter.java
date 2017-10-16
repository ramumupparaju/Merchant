package com.incon.connect.ui.scan;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;


/**
 * Created on 31 May 2017 11:19 AM.
 */
public class ScanTabPresenter extends BasePresenter<ScanTabContract.View> implements
        ScanTabContract.Presenter {

    private static final String TAG = ScanTabPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void userInfoUsingPhoneNumber(String phoneNumber) {
        getView().showProgress(appContext.getString(R.string.progress_user_details));
        DisposableObserver<UserInfoResponse> observer = new
                DisposableObserver<UserInfoResponse>() {
                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        getView().userInfo(userInfoResponse);
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
        AppApiService.getInstance().userInfoUsingPhoneNumber(phoneNumber).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void userInfoUsingQrCode(String qrCode) {
        getView().showProgress(appContext.getString(R.string.progress_user_details));
        DisposableObserver<UserInfoResponse> observer = new
                DisposableObserver<UserInfoResponse>() {
                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        getView().userInfo(userInfoResponse);
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
        AppApiService.getInstance().userInfoUsingQrCode(qrCode).subscribe(observer);
        addDisposable(observer);
    }

}
