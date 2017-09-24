package com.incon.connect.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Register extends BaseObservable {

    private byte roleType;
    private String verificationCode;
    private String verifiedEmail = "";

    private RegistrationBody  registrationBody;

    public Register() {
        registrationBody = new RegistrationBody();
    }

    @Bindable
    public byte getRoleType() {
        return roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }

    @Bindable
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Bindable
    public RegistrationBody getRegistrationBody() {
        return registrationBody;
    }

    @Bindable
    public Patient getPatient() {
        return registrationBody.getPatient();
    }

    @Bindable
    public boolean isParent() {
        return roleType == 0;
    }

    public String getVerifiedEmail() {
        return verifiedEmail;
    }

    public void setEmailVerified(String emailVerified) {
        verifiedEmail = emailVerified;
    }


}
