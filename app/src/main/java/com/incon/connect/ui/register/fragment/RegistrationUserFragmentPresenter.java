package com.incon.connect.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.dto.registration.UserInfo;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.DateUtils;
import com.incon.connect.utils.ValidationUtils;

import java.util.Calendar;

import static com.incon.connect.AppConstants.RegistrationValidation.DOB_FUTURE_DATE;
import static com.incon.connect.AppConstants.RegistrationValidation.DOB_PERSON_LIMIT;
import static com.incon.connect.AppConstants.VALIDATION_SUCCESS;

/**
 * Created on 08 Jun 2017 8:31 PM.
 */
public class RegistrationUserFragmentPresenter extends
        BasePresenter<RegistrationUserStoreFragmentContract.View> implements
        RegistrationUserStoreFragmentContract.Presenter {

    private Context appContext;
    private static final String TAG = RegistrationUserFragmentPresenter.class.getName();
    private UserInfo userInfo;


    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    /*public Observable<SendOtpResponse> getSendOtpObserver(HashMap<String, String> email) {
        return TueoApiService.getInstance().sendOtp(email);
    }

    public Observable<ValidateOtpResponse> getVerifyOtpObserver(HashMap<String, String> verify) {
        return TueoApiService.getInstance().verifyOtp(verify);
    }

    public Observable<ValidateEmailResponse> getValidateEmailObserver(String email) {
        return TueoApiService.getInstance().validateEmail(email);
    }

    @Override
    public void sendOtp(HashMap<String, String> email) {
        getView().showProgress(appContext.getString(R.string.progress_sendotp));
        Observable<SendOtpResponse> sendOtpObserver = getSendOtpObserver(email);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().hideProgress();
                getView().showVerifyEmailPopup();
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        sendOtpObserver.subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void verifyOtp(HashMap<String, String> verifyOtpMap) {
        getView().showProgress(appContext.getString(R.string.progress_verifycode));
        Observable<ValidateOtpResponse> veriftOtpObserver = getVerifyOtpObserver(verifyOtpMap);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                ValidateOtpResponse validateOtpResponse = (ValidateOtpResponse) o;
                Logger.v(TAG, "==> responseCallback : StatusCode = "
                        + validateOtpResponse.getStatusCode());

                int statusCode = validateOtpResponse.getStatusCode();
                switch (statusCode) {
                    case 200:
                        getView().navigateToNext();
                        break;
                    default:
                        String errorMsg = validateOtpResponse.getMessage();
                        getView().showErrorMessage(errorMsg);
                        break;
                }
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        veriftOtpObserver.subscribe(observer);
        addDisposable(observer);
    }*/

    /**
     * NOTHING TO DO WITH THIS METHOD
     *
     * @param userInfo
     */
    @Override
    public void registerUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /*@Override
    public void validateEmailInUse(String email) {
        getView().showProgress(appContext.getString(R.string.progress_verifyemail));
        Observable<ValidateEmailResponse> veriftOtpObserver = getValidateEmailObserver(email);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().hideProgress();
                ValidateEmailResponse validateEmailResponse = (ValidateEmailResponse) o;
                getView().onValidateEmailInUseCheck(validateEmailResponse);
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
            }
        };
        veriftOtpObserver.subscribe(observer);
        addDisposable(observer);
    }*/

    public Pair<String, Integer> validateUserInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 6; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                if (emptyValidation && TextUtils.isEmpty(userInfo.getName())) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(userInfo.getPhoneNumber());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                } else if (!ValidationUtils.isPhoneNumberValid(userInfo.getPhoneNumber())) {
                    return AppConstants.RegistrationValidation.PHONE_MIN_DIGITS;
                }
                break;

            case 2:
                boolean genderTypeEmpty = TextUtils.isEmpty(userInfo
                        .getGenderType());
                if (emptyValidation && genderTypeEmpty) {
                    return AppConstants.RegistrationValidation.GENDER_REQ;
                }
                break;

            case 3:
                boolean dobEmpty = TextUtils.isEmpty(userInfo.getDob());
                if (emptyValidation && dobEmpty) {
                    return AppConstants.RegistrationValidation.DOB_REQ;
                } else if (!dobEmpty) {
                    return validateDob();
                }
                break;

            case 4:
                boolean emailEmpty = TextUtils.isEmpty(userInfo.getEmailId());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                } else if (!ValidationUtils.isValidEmail(userInfo.getEmailId())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 5:
                boolean passwordEmpty = TextUtils.isEmpty(userInfo.getPassword());
                if (emptyValidation && passwordEmpty) {
                    return AppConstants.RegistrationValidation.PASSWORD_REQ;
                } else if (!ValidationUtils
                        .isPasswordValid(userInfo.getPassword())) {
                    return AppConstants.RegistrationValidation.PASSWORD_PATTERN_REQ;
                }
                break;

            case 6:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(userInfo
                        .getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return AppConstants.RegistrationValidation.RE_ENTER_PASSWORD_REQ;
                } else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(userInfo.getPassword());
                    if (passwordEmpty1 || (!userInfo.getPassword()
                            .equals(userInfo.getConfirmPassword()))) {
                        return AppConstants.RegistrationValidation
                                .RE_ENTER_PASSWORD_DOES_NOT_MATCH;
                    }

                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }

    private int validateDob() {
        Calendar dobDate = Calendar.getInstance();
        long dobInMillis = DateUtils.convertStringFormatToMillis(
                userInfo.getDob(), AppConstants.DateFormatterConstants.YYYY_MM_DD);
        dobDate.setTimeInMillis(dobInMillis);
        // future date check
        if (ValidationUtils.isFutureDate(dobDate)) {
            return DOB_FUTURE_DATE;
        }

        int returnedYear = ValidationUtils.calculateAge(dobDate);
        if (returnedYear < AppConstants.AgeConstants.USER_DOB) {
            return DOB_PERSON_LIMIT;
        }
        return VALIDATION_SUCCESS;
    }
}
