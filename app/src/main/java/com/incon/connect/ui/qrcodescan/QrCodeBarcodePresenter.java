package com.incon.connect.ui.qrcodescan;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class QrCodeBarcodePresenter extends BasePresenter<QrCodeBarcodeContract.View> implements
        QrCodeBarcodeContract.Presenter {

//    private final Register register;
    private Context appContext;
    private static final String TAG = QrCodeBarcodePresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    public QrCodeBarcodePresenter(/*Register register*/) {
//        this.register = register;
    }

  /*  public Register getRegister() {
//        return register;
    }*/

    @Override
    public void getUserScannedInfo(String password) {

        getView().showProgress(appContext.getString(R.string.progress_login));
        Observable<UserInfoResponse> loginObserver = userInfoData(password);
        DisposableObserver<UserInfoResponse> observer = new DisposableObserver<UserInfoResponse>() {
            @Override
            public void onNext(UserInfoResponse loginResponse) {
                getView().navigateToQrCodeBarcodeScree(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().navigateToQrCodeBarcodeScree(null);
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        loginObserver.subscribe(observer);
        addDisposable(observer);
    }
    public Observable<UserInfoResponse> userInfoData(String uuid) {
        return AppApiService.getInstance().userInfoData(uuid);
    }
}
