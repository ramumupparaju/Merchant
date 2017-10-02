package com.incon.connect.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.data.login.LoginDataManagerImpl;
import com.incon.connect.dto.Location.LocationPostData;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class RegistrationMapPresenter extends BasePresenter<RegistrationMapContract.View> implements
        RegistrationMapContract.Presenter {

    private Context appContext;
    private LoginDataManagerImpl loginDataManagerImpl;
    private static final String TAG = RegistrationMapPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
    }




    public Observable<LocationPostData> getLoginObserver(String pincode) {
        return AppApiService.getInstance().locationPinCode(pincode);
    }

    @Override
    public void doPostalCode(String address) {
        getView().showProgress(appContext.getString(R.string.progress_login));
        Observable<LocationPostData> loginObserver
                = getLoginObserver("http://maps.googleapis.com/maps/api/"
                + "geocode/json?address=505211&region=us");
        DisposableObserver<LocationPostData> observer = new DisposableObserver<LocationPostData>() {
            @Override
            public void onNext(LocationPostData loginResponse) {
                getView().navigateToHomePage(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().navigateToHomePage(null);
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
}
