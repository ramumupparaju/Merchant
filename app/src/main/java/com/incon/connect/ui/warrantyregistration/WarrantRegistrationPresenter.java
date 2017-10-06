package com.incon.connect.ui.warrantyregistration;


import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

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
        getView().showProgress(appContext.getString(R.string.progress_login));
        DisposableObserver<WarrantyRegistrationResponse> observer =
                new DisposableObserver<WarrantyRegistrationResponse>() {
                    @Override
                    public void onNext(WarrantyRegistrationResponse loginResponse) {
                        getView().loadModelNumberData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                        getView().loadModelNumberData(); //TODO remove
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgress();
                    }
                };
        AppApiService.getInstance().modelNumberSearch(modelNumberToSearch).subscribe(observer);
        addDisposable(observer);
    }

}
