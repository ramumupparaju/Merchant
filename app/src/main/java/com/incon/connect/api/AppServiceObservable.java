package com.incon.connect.api;

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
import com.incon.connect.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.dto.addoffer.AddOfferRequest;
import com.incon.connect.dto.asignqrcode.AssignQrCode;
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

    @GET("user/requestotp/{phoneNumber}/register")
    Observable<Object> registerRequestOtp(@Path("phoneNumber") String phoneNumber);

    @GET("user/requestotp/{phoneNumber}/password")
    Observable<Object> registerRequestPasswordOtp(@Path("phoneNumber") String phoneNumber);

    @Multipart
    @POST("merchant/logoupdate/{storeId}")
    Observable<Object> uploadStoreLogo(@Path("storeId") String storeId,
                                       @Part MultipartBody.Part storeLogo);

    @POST("account/sendOtp")
    Observable<SendOtpResponse> sendOtp(@Body HashMap<String, String> email);

    @POST("user/validateotp")
    Observable<LoginResponse> validateOtp(@Body HashMap<String, String> verify);

    @POST("merchant/forgotpassword")
    Observable<ApiBaseResponse> forgotPassword(@Body HashMap<String, String> phoneNumber);

    @POST("merchant/changepassword")
    Observable<LoginResponse> changePassword(@Body HashMap<String, String> password);

    @GET("product/checkqropnestatus/{qrCode}")
    Observable<Object> checkQrCodestatus(@Path("qrCode") String qrCode);

    @GET("merchant/history/purchased/{userId}")
    Observable<List<PurchasedHistoryResponse>> purchasedApi(@Path("userId") int userId);

    @POST("product/assign")
    Observable<Object> assignQrCodeToProduct(@Body AssignQrCode qrCode);

    //    TODO Change purchased to interest
    @GET("merchant/history/purchased/{userId}")
    Observable<List<InterestHistoryResponse>> interestApi(@Path("userId") int userId);

    //    TODO Change purchased to return
    @GET("merchant/history/purchased/{userId}")
    Observable<List<ReturnHistoryResponse>> returnApi(@Path("userId") int userId);

    @GET("merchant/buy-requests/{userId}")
    Observable<List<BuyRequestResponse>> buyRequestApi(@Path("userId") int userId);

    @POST("offers/addoffers")
    Observable<AddOfferMerchantFragmentResponse> addOffer(@Body AddOfferRequest addOfferRequest);

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
    Observable<Object> addingNewModel(@Path("merchantId") int merchantId, @Body AddNewModel
            addNewModelBody);

    @POST("warranty/register")
    Observable<Object> warrantyRegisterApi(@Body WarrantyRegistration warrantyRegistration);

    @POST("warranty/validateotp")
    Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(@Body HashMap<String, String>
                                                                        verify);

    @GET("warranty/requestotp/{phoneNumber}/register")
    Observable<Object> warrantyRequestOtp(@Path("phoneNumber") String phoneNumber);

    @POST("user/newuser/{phoneNumber}")
    Observable<UserInfoResponse> newUserRegistation(@Path("phoneNumber")
                                                            String phoneNumber);

    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody);

}
