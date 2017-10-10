package com.incon.connect.ui.warrantyregistration;


import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 9/26/2017.
 */
public class WarrantRegistrationPresenter extends BasePresenter<WarrantRegistrationContract.View>
        implements WarrantRegistrationContract.Presenter {

    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void doModelSearchApi(String modelNumberToSearch) {
        getView().showProgress(appContext.getString(R.string.progress_loading));
        DisposableObserver<List<ModelSearchResponse>> observer =
                new DisposableObserver<List<ModelSearchResponse>>() {
                    @Override
                    public void onNext(List<ModelSearchResponse> searchResponseList) {
                        getView().loadModelNumberData(searchResponseList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                        if (errorDetails.first != AppConstants.ErrorCodes.NO_NETWORK) {
                            getView().loadModelNumberData(null);
                        }
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgress();
                    }
                };
        AppApiService.getInstance().modelNumberSearch(modelNumberToSearch).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void doWarrantyRegistrationApi(WarrantyRegistration warrantyRegistration) {
        getView().showProgress(appContext.getString(R.string.progress_warranty_registering));
        DisposableObserver<Object> observer = new
                DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object warrantyRegisteredResponse) {
                        getView().warrantyRegistered(warrantyRegisteredResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        WarrantRegistrationContract.View view = getView();
                        view.hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        view.handleException(errorDetails);
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgress();
                    }
                };
        AppApiService.getInstance().warrantyRegisterApi(warrantyRegistration).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void validateUserOTP(HashMap<String, String> verify) {
        getView().showProgress(appContext.getString(R.string.validating_code));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                WarrantRegistrationContract.View view = getView();
                view.hideProgress();
                view.validateUserOTP();
            }
            @Override
            public void onError(Throwable e) {
                WarrantRegistrationContract.View view = getView();
                view.hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                view.handleException(errorDetails);
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
