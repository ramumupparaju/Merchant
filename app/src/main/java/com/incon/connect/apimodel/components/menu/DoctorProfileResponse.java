package com.incon.connect.apimodel.components.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DoctorData doctorData;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DoctorData getData() {
        return doctorData;
    }

    public void setData(DoctorData data) {
        this.doctorData = data;
    }
}