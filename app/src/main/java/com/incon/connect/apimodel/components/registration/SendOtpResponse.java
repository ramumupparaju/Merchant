package com.incon.connect.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.apimodel.base.ApiBaseResponse;

public class SendOtpResponse extends ApiBaseResponse {

    @SerializedName("errors")
    @Expose
    private Object errors;

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
