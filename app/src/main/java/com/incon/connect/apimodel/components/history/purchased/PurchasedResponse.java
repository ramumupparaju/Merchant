package com.incon.connect.apimodel.components.history.purchased;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchasedResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    private String positionText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setPositionText() {
        positionText = "position :" + id;
    }

    public String getPositionText() {
        return positionText;
    }
}