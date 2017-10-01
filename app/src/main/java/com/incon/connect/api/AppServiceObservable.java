package com.incon.connect.api;

import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.base.ApiBaseResponse;
import com.incon.connect.apimodel.components.changepassword.ChangePasswordResponse;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.registration.SendOtpResponse;
import com.incon.connect.apimodel.components.registration.ValidateEmailResponse;
import com.incon.connect.apimodel.components.registration.ValidateOtpResponse;
import com.incon.connect.dto.Location.LocationPostData;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.dto.registration.Register;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AppServiceObservable {
//http://maps.googleapis.com/maps/api/geocode/json?address=505211&region=us

    @POST("login")
    Observable<LoginResponse> login(@Body LoginUserData loginUserData);

    @POST("merchant/register")
    Observable<LoginResponse> register(@Body Register registrationBody);

    @POST("account/sendOtp")
    Observable<SendOtpResponse> sendOtp(@Body HashMap<String, String> email);

    @POST("account/validateOtp")
    Observable<ValidateOtpResponse> validateOtp(@Body HashMap<String, String> verify);

    @GET("account/validateEmail")
    Observable<ValidateEmailResponse> validateEmail(@Query(
            AppConstants.ApiRequestKeyConstants.QUERY_EMAIL) String email);

    @GET("")
    Observable<LocationPostData> doGetLocationPinCode(@Url String url);

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
/*

    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody,
                                    @Header(TueoConstants.
                                            ApiRequestKeyConstants.HEADER_API_KEY)
                                            String apiKeyValue,
                                    @Header(TueoConstants.ApiRequestKeyConstants.
                                            HEADER_AUTHORIZATION)
                                            String authorization);
*/

}
