package com.incon.connect.api;

import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.changepassword.ChangePasswordResponse;
import com.incon.connect.apimodel.components.defaults.DefaultsResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.apimodel.components.registration.ValidateOtpResponse;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.dto.notifications.PushRegistrarBody;
import com.incon.connect.dto.registration.Registration;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("user/getuser/scan/{uuid}/")
    Observable<UserInfoResponse> userInfoData(@Path("uuid") String uuid);

    @POST("validateotp")
    Observable<LoginResponse> validateOtp(@Body HashMap<String, String> verify);

    @POST("account/validateChangeEmailOTP")
    Observable<ValidateOtpResponse> validateChangeEmailOtp(@Body HashMap<String, String> verify,
                                                           @Header(AppConstants.
                                                                   ApiRequestKeyConstants.
                                                                   HEADER_API_KEY)
                                                                   String apiKeyValue,
                                                           @Header(AppConstants.
                                                                   ApiRequestKeyConstants.
                                                                   HEADER_AUTHORIZATION)
                                                                   String authorization);

    @POST("merchant/forgotpassword")
    Observable<ApiBaseResponse> forgotPassword(@Body HashMap<String, String> phoneNumber);

    @POST("merchant/changepassword")
    Observable<ChangePasswordResponse> changePassword(@Body HashMap<String, String> password);


    @POST("product/search/{modelNumber}")
    Observable<WarrantyRegistrationResponse> modelNumberSearch(@Path("modelNumber")
                                                                       String modelNumber);

    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody);

}
