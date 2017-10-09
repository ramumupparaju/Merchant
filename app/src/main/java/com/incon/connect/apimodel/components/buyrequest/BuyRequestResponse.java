package com.incon.connect.apimodel.components.buyrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyRequestResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("merchantId")
    @Expose
    private Integer merchantId;
    @SerializedName("qrcodeid")
    @Expose
    private Integer qrcodeid;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getQrcodeid() {
        return qrcodeid;
    }

    public void setQrcodeid(Integer qrcodeid) {
        this.qrcodeid = qrcodeid;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

}