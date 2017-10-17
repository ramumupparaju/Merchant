package com.incon.connect.dto.addnewmodel;

import android.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewModel extends BaseObservable {
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

}