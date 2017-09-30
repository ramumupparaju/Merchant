package com.incon.connect.api;

import com.incon.connect.AppConstants;
import com.incon.connect.BuildConfig;
import com.incon.connect.apimodel.components.changepassword.ChangePasswordResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.apimodel.components.registration.ValidateEmailResponse;
import com.incon.connect.apimodel.components.registration.ValidateOtpResponse;
import com.incon.connect.custom.exception.NoConnectivityException;
import com.incon.connect.dto.Location.LocationPostData;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.utils.NetworkUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppApiService implements AppConstants {

    private static AppApiService apiDataService;
    private AppServiceObservable serviceInstance;

    private AppApiService() {
        ServiceGenerator serviceGenerator = new ServiceGenerator(BuildConfig.SERVICE_ENDPOINT);
        serviceInstance = serviceGenerator.getTueoHealthService();
    }

    public static AppApiService getInstance() {
        if (apiDataService == null) {
            apiDataService = new AppApiService();
        }
        return apiDataService;
    }

    private <T> Observable<T> checkNetwork() {
        //Returns NetworkStatusException when there is no network
        //Otherwise return nothing
        if (NetworkUtil.isOnline()) {
            return Observable.never();
        } else {
            return Observable.error(new NoConnectivityException());
        }
    }

    private <T> Observable<T> addNetworkCheck(Observable<T> observable) {
        Observable<T> network = checkNetwork();
        return network
                .ambWith(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> login(LoginUserData loginUserData) {
        return addNetworkCheck(serviceInstance
                .login(loginUserData));
    }

    public Observable<LocationPostData> login(String loginUserData) {
        return addNetworkCheck(serviceInstance
                .doGetLocationPinCode(loginUserData));
    }

    public Observable<ChangePasswordResponse> resetPassword(HashMap<String, String> password) {
        return addNetworkCheck(serviceInstance.resetPassword(password));
    }

    public Observable<ValidateOtpResponse> forgotPassword(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.forgotPassword(email));
    }

    /*public Observable<RegistrationResponse> register(RegistrationBody registrationBody) {
        return addNetworkCheck(serviceInstance.register(registrationBody));
    }*/

    public Observable<SendOtpResponse> sendOtp(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.sendOtp(email));
    }

    public Observable<ValidateOtpResponse> verifyOtp(HashMap<String, String> verify) {
        return addNetworkCheck(serviceInstance.validateOtp(verify));
    }

    public Observable<ValidateEmailResponse> validateEmail(String email) {
        return addNetworkCheck(serviceInstance.validateEmail(email));
    }
}
