package com.incon.connect.apimodel.components.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.apimodel.base.ApiBaseResponse;

public class LoginResponse extends ApiBaseResponse {

    @SerializedName("data")
    @Expose
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}