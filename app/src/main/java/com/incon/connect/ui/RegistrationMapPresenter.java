package com.incon.connect.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.Location.LocationPostData;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

public class RegistrationMapPresenter extends BasePresenter<RegistrationMapContract.View> implements
        RegistrationMapContract.Presenter {

    private static final String TAG = RegistrationMapPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void doPostalCode(String pincode) {
        getView().showProgress(appContext.getString(R.string.progress_location));
        DisposableObserver<LocationPostData> observer = new DisposableObserver<LocationPostData>() {
            @Override
            public void onNext(LocationPostData locationPostData) {
                getView().moveMarkerToThisLocation(locationPostData);
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
        AppApiService.getInstance().locationPinCode(pincode).subscribe(observer);
        addDisposable(observer);
    }
}
