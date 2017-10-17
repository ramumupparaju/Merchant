package com.incon.connect.api;

import com.incon.connect.AppConstants;
import com.incon.connect.BuildConfig;
import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.apimodel.components.buyrequest.BuyRequestResponse;
import com.incon.connect.apimodel.components.defaults.DefaultsResponse;
import com.incon.connect.apimodel.components.history.purchased.InterestHistoryResponse;
import com.incon.connect.apimodel.components.history.purchased.PurchasedHistoryResponse;
import com.incon.connect.apimodel.components.history.purchased.ReturnHistoryResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.connect.custom.exception.NoConnectivityException;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.dto.addoffer.AddOfferRequest;
import com.incon.connect.dto.asignqrcode.AssignQrCode;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.dto.notifications.PushRegistrarBody;
import com.incon.connect.dto.registration.Registration;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.utils.NetworkUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class AppApiService implements AppConstants {

    private static AppApiService apiDataService;
    private AppServiceObservable serviceInstance;

    private AppApiService() {
        ServiceGenerator serviceGenerator = new ServiceGenerator(BuildConfig.SERVICE_ENDPOINT);
        serviceInstance = serviceGenerator.getConnectService();
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

    public Observable<DefaultsResponse> defaultsApi() {
        return addNetworkCheck(serviceInstance.defaultsApi());
    }

    public Observable<LoginResponse> login(LoginUserData loginUserData) {
        return addNetworkCheck(serviceInstance
                .login(loginUserData));
    }

    public Observable<LoginResponse> changePassword(HashMap<String, String> password) {
        return addNetworkCheck(serviceInstance.changePassword(password));
    }

    public Observable<ApiBaseResponse> forgotPassword(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.forgotPassword(email));
    }

    public Observable<LoginResponse> register(Registration registrationBody) {
        return addNetworkCheck(serviceInstance.register(registrationBody));
    }

    public Observable<Object> registerRequestOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.registerRequestOtp(phoneNumber));
    }

    public Observable<Object> registerRequestPasswordOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.registerRequestPasswordOtp(phoneNumber));
    }

    public Observable<Object> uploadStoreLogo(int storeId, MultipartBody.Part storeLogo) {
        return addNetworkCheck(serviceInstance.uploadStoreLogo(String.valueOf(storeId), storeLogo));
    }

    public Observable<SendOtpResponse> sendOtp(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.sendOtp(email));
    }

    public Observable<LoginResponse> validateOtp(HashMap<String, String> verify) {
        return addNetworkCheck(serviceInstance.validateOtp(verify));
    }

    public Observable<Object> assignQrCodeToProduct(AssignQrCode qrCode) {
        return addNetworkCheck(serviceInstance.assignQrCodeToProduct(qrCode));
    }

    public Observable<List<PurchasedHistoryResponse>> purchasedApi(int userId) {
        return addNetworkCheck(serviceInstance.purchasedApi(userId));
    }

    public Observable<List<InterestHistoryResponse>> interestApi(int userId) {
        return addNetworkCheck(serviceInstance.interestApi(userId));
    }

    public Observable<List<ReturnHistoryResponse>> returnApi(int userId) {
        return addNetworkCheck(serviceInstance.returnApi(userId));
    }

    public Observable<List<BuyRequestResponse>> buyRequestApi(int userId) {
        return addNetworkCheck(serviceInstance.buyRequestApi(userId));
    }

    public Observable<AddOfferMerchantFragmentResponse> addOffer(AddOfferRequest addOfferRequest) {
        return addNetworkCheck(serviceInstance.addOffer(addOfferRequest));
    }


    public Observable<UserInfoResponse> userInfoUsingQrCode(String qrCode) {
        return addNetworkCheck(serviceInstance.userInfoUsingQrCode(qrCode));
    }

    public Observable<UserInfoResponse> userInfoUsingPhoneNumber(String phoneNumber) {
        return addNetworkCheck(serviceInstance.userInfoUsingPhoneNumber(phoneNumber));
    }

    public Observable<UserInfoResponse> newUserRegistation(String phoneNumber) {
        return addNetworkCheck(serviceInstance.newUserRegistation(phoneNumber));
    }

    public Observable<Object> productInfoUsingQrCode(HashMap<String, String> qrCode) {
        return addNetworkCheck(serviceInstance.productInfoUsingQrCode(qrCode));
    }

    public Observable<List<ModelSearchResponse>> modelNumberSearch(String modelNumber) {
        return addNetworkCheck(serviceInstance.modelNumberSearch(modelNumber));
    }

    public Observable<Object> getCategories(int merchantId) {
        return addNetworkCheck(serviceInstance.getCategories(merchantId));
    }

    public Observable<Object> addingNewModel(int merchantId, AddNewModel addNewModelBody) {
        return addNetworkCheck(serviceInstance.addingNewModel(merchantId, addNewModelBody));
    }

    public Observable<Object> warrantyRegisterApi(WarrantyRegistration warrantyRegistration) {
        return addNetworkCheck(serviceInstance.warrantyRegisterApi(warrantyRegistration));
    }

    public Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(HashMap<String, String>
                                                                               verify) {
        return addNetworkCheck(serviceInstance.validateWarrantyOtp(verify));
    }

    public Observable<Object> warrantyRequestOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.warrantyRequestOtp(phoneNumber));
    }

    /* public Observable<Object> assignQrCodeToProduct(AssignQrCode qrCode) {
        return addNetworkCheck(serviceInstance.assignQrCodeToProduct(qrCode));
    }*/

    public Observable<Object> pushTokenApi(PushRegistrarBody pushRegistrarBody) {
        return addNetworkCheck(serviceInstance.pushTokenApi(pushRegistrarBody));
    }
}
