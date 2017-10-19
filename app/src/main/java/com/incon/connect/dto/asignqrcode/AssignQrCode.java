package com.incon.connect.dto.asignqrcode;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 10/12/2017.
 */

public class AssignQrCode extends BaseObservable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("productId")
    @Expose
    private String productId;
    private transient String productName;

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyChange();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}