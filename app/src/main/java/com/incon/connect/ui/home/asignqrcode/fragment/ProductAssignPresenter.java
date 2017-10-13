package com.incon.connect.ui.home.asignqrcode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.dto.asignqrcode.AssignQrCode;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 10/12/2017.
 */

public class ProductAssignPresenter extends BasePresenter<ProductAssignContract.View> implements
        ProductAssignContract.Presenter {
    private static final String TAG = ProductAssignPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    public void assignQrCodeToProduct(AssignQrCode qrCode) {
        getView().showProgress(appContext.getString(R.string.progress_qr_code_product));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object assignQrCodeResponse) {
                getView().productAssignQrCode(assignQrCodeResponse);
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
        AppApiService.getInstance().assignQrCodeToProduct(qrCode).subscribe(observer);
        addDisposable(observer);
    }
}
