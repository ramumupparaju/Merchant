package com.incon.connect.apimodel.components.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.apimodel.components.login.Patient;

import java.util.List;

public class UpdatePatientResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Patient> data = null;

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

    public List<Patient> getData() {
        return data;
    }

    public void setData(List<Patient> data) {
        this.data = data;
    }

}