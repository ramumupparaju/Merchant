package com.incon.connect.ui.home.asignqrcode.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;

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


    public void assignQrCodeToProduct(String qrCode) {
        HashMap<String, String> assignQrCode = new HashMap<>();
        assignQrCode.put(AppConstants.ApiRequestKeyConstants.BODY_PRODUCT_CODE, qrCode);
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
        AppApiService.getInstance().assignQrCodeToProduct(assignQrCode).subscribe(observer);
        addDisposable(observer);
    }
}
