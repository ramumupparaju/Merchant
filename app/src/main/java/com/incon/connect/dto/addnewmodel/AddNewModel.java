package com.incon.connect.dto.addnewmodel;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("productModel")
    @Expose
    private String productModel;

    private  transient String categoryName;
    private transient String divisionName;

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
}