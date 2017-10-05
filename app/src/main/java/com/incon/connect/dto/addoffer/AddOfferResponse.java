package com.incon.connect.dto.addoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOfferResponse {

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

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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