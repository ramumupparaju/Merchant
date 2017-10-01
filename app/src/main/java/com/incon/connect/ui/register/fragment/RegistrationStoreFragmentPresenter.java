package com.incon.connect.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.data.login.LoginDataManagerImpl;
import com.incon.connect.dto.registration.Registration;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MultipartBody;

/**
 * Created on 08 Jun 2017 8:31 PM.
 */
public class RegistrationStoreFragmentPresenter extends
        BasePresenter<RegistrationStoreFragmentContract.View> implements
        RegistrationStoreFragmentContract.Presenter {

    private static final String TAG = RegistrationStoreFragmentPresenter.class.getName();
    private Context appContext;
    private LoginDataManagerImpl loginDataManagerImpl;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
    }

    /**
     * Uploading user and store details to server
     * @param registrationBody
     */
    @Override
    public void register(Registration registrationBody) {
        getView().showProgress(appContext.getString(R.string.progress_registering));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                // save login data to shared preferences
                loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
                getView().uploadStoreLogo();
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
        AppApiService.getInstance().register(registrationBody).subscribe(observer);
        addDisposable(observer);
    }

    /**
     * Uploading store logo to server
     */
    @Override
    public void uploadStoreLogo(int storeId, MultipartBody.Part storeLogo) {
        DisposableObserver<ApiBaseResponse> observer = new DisposableObserver<ApiBaseResponse>() {
            @Override
            public void onNext(ApiBaseResponse loginResponse) {
                // save login data to shared preferences
                loginDataManagerImpl.saveStoreLogo(loginResponse.getMessage());
                getView().hideProgress();
                getView().navigateToHomeScreen();
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
        AppApiService.getInstance().uploadStoreLogo(storeId, storeLogo).subscribe(observer);
        addDisposable(observer);
    }

}
