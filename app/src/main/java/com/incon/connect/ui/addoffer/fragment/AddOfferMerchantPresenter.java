package com.incon.connect.ui.addoffer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.dto.addoffer.AddOfferRequest;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 10/8/2017.
 */

public class AddOfferMerchantPresenter extends BasePresenter<AddOfferMerchantContract.View>
        implements AddOfferMerchantContract.Presenter {
    private static final String TAG = AddOfferMerchantPresenter.class.getName();
    private Context appContext;
    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }



    public void addOffer(AddOfferRequest addOfferRequest) {
        getView().showProgress(appContext.getString(R.string.progress_addoffer_merchant_fragment));
        DisposableObserver<AddOfferMerchantFragmentResponse> observer = new
                DisposableObserver<AddOfferMerchantFragmentResponse>() {
                    @Override
                    public void onNext(AddOfferMerchantFragmentResponse
                                               addOfferMerchantFragmentResponse) {
                        getView().hideProgress();
                        getView().loadAddOfferMerchant(
                                addOfferMerchantFragmentResponse);

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
        AppApiService.getInstance().addOffer(addOfferRequest).subscribe(observer);
        addDisposable(observer);

    }


}
