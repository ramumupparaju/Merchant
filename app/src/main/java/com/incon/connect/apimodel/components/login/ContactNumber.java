package com.incon.connect.apimodel.components.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactNumber {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("contact_number_type_id")
    @Expose
    private Integer contactNumberTypeId;
    @SerializedName("account_contact_numbers")
    @Expose
    private AccountContactNumbers accountContactNumbers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getContactNumberTypeId() {
        return contactNumberTypeId;
    }

    public void setContactNumberTypeId(Integer contactNumberTypeId) {
        this.contactNumberTypeId = contactNumberTypeId;
    }

    public AccountContactNumbers getAccountContactNumbers() {
        return accountContactNumbers;
    }

    public void setAccountContactNumbers(AccountContactNumbers accountContactNumbers) {
        this.accountContactNumbers = accountContactNumbers;
    }

}