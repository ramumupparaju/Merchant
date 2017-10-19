package com.incon.connect.dto.warrantyregistration;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarrantyRegistration extends BaseObservable implements Parcelable {

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;

    @SerializedName("brandId")
    @Expose
    private String brandId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("batchNumber")
    @Expose
    private String batchNumber;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("invoiceNumber")
    @Expose
    private String invoiceNumber;
    private transient String categoryName;
    private transient String divisionName;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyChange();
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
        notifyChange();
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        notifyChange();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyChange();
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        notifyChange();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        notifyChange();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public WarrantyRegistration() {
    }

    protected WarrantyRegistration(Parcel in) {
        mobileNumber = in.readString();
        modelNumber = in.readString();
        categoryId = in.readByte() == 0x00 ? null : in.readString();
        divisionId = in.readByte() == 0x00 ? null : in.readString();
        brandId = in.readString();
        price = in.readString();
        batchNumber = in.readString();
        serialNumber = in.readString();
        merchantId = in.readByte() == 0x00 ? null : in.readString();
        productId = in.readByte() == 0x00 ? null : in.readString();
        customerId = in.readByte() == 0x00 ? null : in.readString();
        status = in.readString();
        invoiceNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeString(modelNumber);
        if (categoryId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(categoryId);
        }
        if (divisionId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(divisionId);
        }
        dest.writeString(brandId);
        dest.writeString(price);
        dest.writeString(batchNumber);
        dest.writeString(serialNumber);
        if (merchantId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(merchantId);
        }
        if (productId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(productId);
        }
        if (customerId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(customerId);
        }
        dest.writeString(status);
        dest.writeString(invoiceNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WarrantyRegistration> CREATOR = new
            Parcelable.Creator<WarrantyRegistration>() {
                @Override
                public WarrantyRegistration createFromParcel(Parcel in) {
                    return new WarrantyRegistration(in);
                }

                @Override
                public WarrantyRegistration[] newArray(int size) {
                    return new WarrantyRegistration[size];
                }
            };
}