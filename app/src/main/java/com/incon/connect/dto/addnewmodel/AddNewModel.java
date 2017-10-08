package com.incon.connect.dto.addnewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class AddNewModel extends BaseObservable {
    private String name;
    private String modelNumber;
    private String price;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        notifyChange();
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyChange();
    }
}