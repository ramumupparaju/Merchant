package com.incon.connect.ui.scan;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.qrcodeproduct.ProductInfoResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;


/**
 * Created on 31 May 2017 11:19 AM.
 */
public class ProductScanPresenter extends BasePresenter<ProductScanContract.View> implements
        ProductScanContract.Presenter {

    private static final String TAG = ProductScanPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void productInfoUsingQrCode(String qrCode) {
        HashMap<String, String> qrCodeMap = new HashMap<>();
        qrCodeMap.put(AppConstants.ApiRequestKeyConstants.BODY_PRODUCT_CODE,
                qrCode);

        getView().showProgress(appContext.getString(R.string.progress_user_details));
        DisposableObserver<ProductInfoResponse> observer = new
                DisposableObserver<ProductInfoResponse>() {
                    @Override
                    public void onNext(ProductInfoResponse productInfoResponse) {
                        getView().hideProgress();
                        getView().productInfo(productInfoResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                    }

                    @Override
                    public void onComplete() {

                    }
                };
        AppApiService.getInstance().productInfoUsingQrCode(qrCodeMap).subscribe(observer);
        addDisposable(observer);
    }
}
