package com.incon.connect.ui.warrantyregistration;


import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

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

}
