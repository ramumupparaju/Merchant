package com.incon.connect.dto.changepassword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.incon.connect.AppConstants;

public class Password  extends BaseObservable implements AppConstants {

    private String newPassword;
    private String confirmPassword;

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
