package com.incon.connect.ui.changepassword;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.changepassword.ChangePasswordResponse;
import com.incon.connect.dto.changepassword.Password;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;
import com.incon.connect.utils.ValidationUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.View> implements
        ChangePasswordContract.Presenter {

    private static final String TAG = ChangePasswordPresenter.class.getName();
    private Context appContext;
    private Password password;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void resetPassword(HashMap<String, String> password) {
        getView().showProgress(appContext.getString(R.string.progress_changepassword));
        Observable<ChangePasswordResponse> resetPasswordObserver = getResetPasswordObserver(
                password);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().navigateToLoginPage();
            }
            @Override
            public void onError(Throwable e) {
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        resetPasswordObserver.subscribe(observer);
        addDisposable(observer);
    }

    public Observable<ChangePasswordResponse> getResetPasswordObserver(HashMap<String, String>
                                                                               password) {
        return AppApiService.getInstance().resetPassword(password);
    }

    /**
     * NOTHING TO DO WITH THIS METHOD
     * @param password
     */
    @Override
    public void setChangePassword(Password password) {
        this.password = password;
    }

    public Pair<String, Integer> validateUserInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 1; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != AppConstants.VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        }
        else {
            fieldId =  validateFields(Integer.parseInt(tag), false);
        }

        return  new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                boolean passwordEmpty = TextUtils.isEmpty(password.getNewPassword());
                if (emptyValidation && passwordEmpty) {
                    return AppConstants.PasswordValidation.NEWPWD_REQ;
                }
                else if (!passwordEmpty && !ValidationUtils
                        .isPasswordValid(password.getNewPassword())) {
                    return AppConstants.PasswordValidation.NEWPWD_PATTERN_REQ;
                }
                break;

            case 1:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(password
                        .getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return AppConstants.PasswordValidation.CONFIRMPWD_REQ;
                }
                else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(password.getNewPassword());
                    if (passwordEmpty1 || (!password.getNewPassword()
                            .equals(password.getConfirmPassword()))) {
                        return AppConstants.PasswordValidation
                                .PWD_NOTMATCH;
                    }

                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

}
