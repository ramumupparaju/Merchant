package com.incon.connect.dto.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.AppConstants;
import com.incon.connect.utils.Validator;

public class User extends BaseObservable implements AppConstants {


    @SerializedName("userid")
    @Expose
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
//        notifyPropertyChanged(com.tueohealth.databinding.);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
//        notifyPropertyChanged(com.tueohealth.databinding.password);
    }

    public int validateLogin() {
        int isValid = AppConstants.VALIDATION_SUCCESS;
        if (TextUtils.isEmpty(email)) {
            isValid = LoginValidation.EMAIL_REQ;
        } else if (!Validator.isValidEmail(email)) {
            isValid = LoginValidation.EMAIL_NOTVALID;
        } else if (TextUtils.isEmpty(password)) {
            isValid = LoginValidation.PASSWORD_REQ;
        }
        return isValid;
    }
}
