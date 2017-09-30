package com.incon.connect.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.data.login.LoginDataManagerImpl;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements
        LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getName();
    private Context appContext;
    private LoginDataManagerImpl loginDataManagerImpl;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
    }

    @Override
    public void doLogin(LoginUserData loginUserData) {
        getView().showProgress(appContext.getString(R.string.progress_login));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                saveLoginData(loginResponse);
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
        AppApiService.getInstance().login(loginUserData).subscribe(observer);
        addDisposable(observer);
    }

    public void saveLoginData(LoginResponse loginResponse) {
        // save login response to shared preferences
        loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
    }
}
