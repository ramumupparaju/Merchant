package com.incon.connect.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.connect.data.login.LoginDataManagerImpl;
import com.incon.connect.dto.registration.Registration;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.ui.validateotp.ValidateOtpContract;
import com.incon.connect.ui.validateotp.ValidateOtpPresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;

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
     *
     * @param registrationBody
     */
    @Override
    public void register(Registration registrationBody) {
        getView().showProgress(appContext.getString(R.string.progress_registering));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                getView().uploadStoreLogo(loginResponse.getStore().getId());
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
        AppApiService.getInstance().register(registrationBody).subscribe(observer);
        addDisposable(observer);
    }

    /**
     * Uploading store logo to server
     */
    @Override
    public void uploadStoreLogo(int storeId, MultipartBody.Part storeLogo) {
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object loginResponse) {
                // save login data to shared preferences
                getView().hideProgress();
                getView().validateOTP();
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

    @Override
    public void validateOTP(HashMap<String, String> verify) {
        getView().showProgress(appContext.getString(R.string.validating_code));
        ValidateOtpPresenter otpPresenter = new ValidateOtpPresenter();
        otpPresenter.initialize(null);
        otpPresenter.setView(otpView);
        otpPresenter.validateOTP(verify);
    }

    @Override
    public void registerRequestOtp(String phoneNumber) {
        getView().showProgress(appContext.getString(R.string.progress_resend));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object loginResponse) {
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
        AppApiService.getInstance().registerRequestOtp(phoneNumber).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void registerRequestPasswordOtp(String phoneNumber) {
        getView().showProgress(appContext.getString(R.string.progress_resend));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object loginResponse) {
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
        AppApiService.getInstance().registerRequestPasswordOtp(phoneNumber).subscribe(observer);
        addDisposable(observer);
    }

    ValidateOtpContract.View otpView = new ValidateOtpContract.View() {
        @Override
        public void validateOTP(LoginResponse loginResponse) {
// save login data to shared preferences
            loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
            getView().hideProgress();
            getView().navigateToHomeScreen();
        }

        @Override
        public void validateWarrantyOTP(ValidateWarrantyOtpResponse warrantyOtpResponse) {
            //DO nothing
        }

        @Override
        public void showProgress(String message) {
            getView().showProgress(message);
        }

        @Override
        public void hideProgress() {
            getView().hideProgress();
        }

        @Override
        public void showErrorMessage(String errorMessage) {
            getView().showErrorMessage(errorMessage);
        }

        @Override
        public void handleException(Pair<Integer, String> error) {
            getView().handleException(error);
        }
    };

    public void saveMerchantInfo(LoginResponse loginResponse) {
        // save login data to shared preferences
        loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
    }

}
