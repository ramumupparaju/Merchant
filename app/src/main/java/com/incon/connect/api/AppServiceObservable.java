package com.incon.connect.api;

import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.changepassword.ChangePasswordResponse;
import com.incon.connect.apimodel.components.defaults.DefaultsResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.apimodel.components.registration.ValidateOtpResponse;
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

    @POST("validateotp")
    Observable<Object> validateOtp(@Body HashMap<String, String> verify);

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

    @POST("account/resetPassword")
    Observable<ChangePasswordResponse> resetPassword(@Body HashMap<String, String> password);

    @POST("account/changePassword")
    Observable<ChangePasswordResponse> changePassword(@Body HashMap<String, String> password);

    @POST("account/changeEmail")
    Observable<ApiBaseResponse> changeEmail(@Body HashMap<String, String> email,
                                            @Header(AppConstants.ApiRequestKeyConstants.
                                                    HEADER_API_KEY)
                                                    String requestApiKey,
                                            @Header(AppConstants.ApiRequestKeyConstants.
                                                    HEADER_AUTHORIZATION)
                                                    String authorization);

    @GET("account/current")
    Observable<LoginResponse> getUserDetails(@Header(AppConstants.
            ApiRequestKeyConstants.HEADER_API_KEY) String apiKeyValue,
                                             @Header(AppConstants.ApiRequestKeyConstants.
                                                     HEADER_AUTHORIZATION) String authorization);

    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody);

}
