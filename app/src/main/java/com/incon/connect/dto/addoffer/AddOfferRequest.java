package com.incon.connect.dto.addoffer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOfferRequest extends BaseObservable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("positiontext")
    @Expose
    private String positionText;
    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("brandType")
    @Expose
    private String brandType;
    @SerializedName("buyingStatus")
    @Expose
    private String buyingStatus;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("dateRequested")
    @Expose
    private String dateRequested;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("offerStartOn")
    @Expose
    private String offerStartOn;

    @SerializedName("offerExpiresOn")
    @Expose
    private String offerExpiresOn;


    public String getOfferStartOn() {
        return offerStartOn;
    }

    public void setOfferStartOn(String offerStartOn) {
        this.offerStartOn = offerStartOn;
    }

    public String getOfferExpiresOn() {
        return offerExpiresOn;
    }

    public void setOfferExpiresOn(String offerExpiresOn) {
        this.offerExpiresOn = offerExpiresOn;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }




    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    @Bindable
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
        notifyChange();
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getBuyingStatus() {
        return buyingStatus;
    }

    public void setBuyingStatus(String buyingStatus) {
        this.buyingStatus = buyingStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

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