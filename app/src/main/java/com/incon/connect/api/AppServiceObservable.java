package com.incon.connect.api;

import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.defaults.DefaultsResponse;
import com.incon.connect.apimodel.components.history.purchased.PurchasedHistoryResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.dto.notifications.PushRegistrarBody;
import com.incon.connect.dto.registration.Registration;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AppServiceObservable {

    @GET("defaults")
    Observable<DefaultsResponse> defaultsApi();

    @POST("login")
    Observable<LoginResponse> login(@Body LoginUserData loginUserData);

    @POST("merchant/register")
    Observable<LoginResponse> register(@Body Registration registrationBody);

    @Multipart
    @POST("merchant/logoupdate/{storeId}")
    Observable<Object> uploadStoreLogo(@Path("storeId") String storeId,
                                       @Part MultipartBody.Part storeLogo);

    @POST("account/sendOtp")
    Observable<SendOtpResponse> sendOtp(@Body HashMap<String, String> email);

    @POST("validateotp")
    Observable<LoginResponse> validateOtp(@Body HashMap<String, String> verify);

    @POST("merchant/forgotpassword")
    Observable<ApiBaseResponse> forgotPassword(@Body HashMap<String, String> phoneNumber);

    @POST("merchant/changepassword")
    Observable<LoginResponse> changePassword(@Body HashMap<String, String> password);

    @GET("merchant/history/purchased/{userId}")
    Observable<List<PurchasedHistoryResponse>> purchasedApi(@Path("userId") int userId);

    @GET("user/getuser/{phoneNumber}")
    Observable<UserInfoResponse> userInfoUsingPhoneNumber(@Path("phoneNumber") String phoneNumber);

    @GET("user/getuser/scan/{qrCode}/")
    Observable<UserInfoResponse> userInfoUsingQrCode(@Path("qrCode") String qrCode);

    @POST("product/getproduct")
    Observable<Object> productInfoUsingQrCode(@Body HashMap<String, String> qrCode);

    @GET("product/search/{modelNumber}")
    Observable<List<ModelSearchResponse>> modelNumberSearch(@Path("modelNumber")
                                                                       String modelNumber);

    @POST("product/addnew/{merchantId}")
    Observable<Object>  addingNewModel(@Path("merchantId") int merchantId, @Body AddNewModel
            addNewModelBody);

    @POST("warranty/register")
    Observable<Object>  warrantyRegisterApi(@Body WarrantyRegistration warrantyRegistration);

    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody);

}
