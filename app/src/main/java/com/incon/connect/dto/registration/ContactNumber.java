package com.incon.connect.dto.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactNumber {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("contact_number_type_id")
    @Expose
    private Integer contactNumberTypeId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getContactNumberTypeId() {
        return contactNumberTypeId;
    }

    public void setContactNumberTypeId(Integer contactNumberTypeId) {
        this.contactNumberTypeId = contactNumberTypeId;
    }

}