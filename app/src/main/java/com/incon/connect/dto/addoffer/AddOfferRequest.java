package com.incon.connect.dto.addoffer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class AddOfferRequest extends BaseObservable {

    private String brandId;
    private String categoryId;
    private String customerId;
    private String divisionId;
    private String fromDate;
    private String merchantId;
    private String modelNumber;
    private String offer;
    private String productId;
    private String purchaseId;
    private String toDate;
    private String scanStartDate;
    private String scanEndDate;

    public AddOfferRequest() {
    }

    @Bindable
    public String getScanStartDate() {
        return scanStartDate;
    }

    public void setScanStartDate(String scanStartDate) {
        this.scanStartDate = scanStartDate;
        notifyChange();
    }

    @Bindable
    public String getScanEndDate() {
        return scanEndDate;
    }

    public void setScanEndDate(String scanEndDate) {
        this.scanEndDate = scanEndDate;
        notifyChange();
    }

    public AddOfferRequest(String brandId, String categoryId, String customerId,
                           String divisionId, String fromDate, String merchantId,
                           String modelNumber, String offer, String productId,
                           String purchaseId, String toDate) {
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.customerId = customerId;
        this.divisionId = divisionId;
        this.fromDate = fromDate;
        this.merchantId = merchantId;
        this.modelNumber = modelNumber;
        this.offer = offer;
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.toDate = toDate;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    @Bindable
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
        notifyChange();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Bindable
    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
        notifyChange();
    }




}