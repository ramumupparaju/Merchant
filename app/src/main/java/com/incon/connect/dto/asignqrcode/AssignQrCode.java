package com.incon.connect.dto.asignqrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 10/12/2017.
 */

public class AssignQrCode {

    @SerializedName("batchNo")
    @Expose
    private String batchNo;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

}