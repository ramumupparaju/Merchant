package com.incon.connect.dto.addnewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.AppConstants;

public class AddNewModel extends BaseObservable implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("mrpPrice")
    @Expose
    private String mrpPrice;
    @SerializedName("productModel")
    @Expose
    private String productModel;
    @SerializedName("productBrand")
    @Expose
    private String productBrand;

    @SerializedName("information")
    @Expose
    private String productId;
    private String description;
    private transient String categoryName;
    private transient String divisionName;

    public AddNewModel() {
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyChange();
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Bindable
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }


    protected AddNewModel(Parcel in) {
        name = in.readString();
        categoryId = in.readString();
        divisionId = in.readString();
        notes = in.readString();
        price = in.readString();
        productModel = in.readString();
        categoryName = in.readString();
        divisionName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(categoryId);
        dest.writeString(divisionId);
        dest.writeString(notes);
        dest.writeString(price);
        dest.writeString(productModel);
        dest.writeString(categoryName);
        dest.writeString(divisionName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AddNewModel> CREATOR = new
            Parcelable.Creator<AddNewModel>() {
                @Override
                public AddNewModel createFromParcel(Parcel in) {
                    return new AddNewModel(in);
                }

                @Override
                public AddNewModel[] newArray(int size) {
                    return new AddNewModel[size];
                }
            };
    public Pair<String, Integer> validateAddNewModel(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 6; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != AppConstants.VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        }
        else {
            fieldId =  validateFields(Integer.parseInt(tag), false);
        }

        return  new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                boolean modelEmpty = TextUtils.isEmpty(productModel);
                if (emptyValidation && modelEmpty) {
                    return AppConstants.AddNewModelValidation.MODEL;
                } else if (!modelEmpty && TextUtils.isEmpty(productId)) {
                    return AppConstants.ProductAssignValidation.INVALID_MODEL;
                }
                break;

            case 2:
                boolean categoryEmpty = TextUtils.isEmpty(categoryName);
                if (emptyValidation && categoryEmpty) {
                    return AppConstants.AddNewModelValidation.CATEGORY;
                }
                break;
            case 3:
                boolean divisionEmpty = TextUtils.isEmpty(divisionName);
                if (emptyValidation && divisionEmpty) {
                    return AppConstants.AddNewModelValidation.DIVISION;
                }
                break;

            case 4:
                boolean brandEmpty = TextUtils.isEmpty(productBrand);
                if (emptyValidation && brandEmpty) {
                    return AppConstants.AddNewModelValidation.BRAND;
                }
                break;

            case 5:
                boolean mrpPriceEmpty = TextUtils.isEmpty(mrpPrice);
                if (emptyValidation && mrpPriceEmpty) {
                    return AppConstants.AddNewModelValidation.MRP_PRICE;
                }
                break;


            case 6:
                boolean priceEmpty = TextUtils.isEmpty(price);
                if (emptyValidation && priceEmpty) {
                    return AppConstants.AddNewModelValidation.PRICE;
                }
                break;
            case 7:
                boolean notesEmpty = TextUtils.isEmpty(notes);
                if (emptyValidation && notesEmpty) {
                    return AppConstants.AddNewModelValidation.NOTE;
                }
                break;


            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }
}