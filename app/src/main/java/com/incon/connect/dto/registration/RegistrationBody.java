package com.incon.connect.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBody extends BaseObservable {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    private String reEnterPassword;
    @SerializedName("timezone_id")
    @Expose
    private Integer timeZone;
    @SerializedName("contact_numbers")
    @Expose
    private List<ContactNumber> contactNumbers = new ArrayList<>();
    @SerializedName("patient")
    @Expose
    private Patient patient;
    @SerializedName("address")
    @Expose
    private Address address;


    private String phone;
    private String phoneType;

    public RegistrationBody() {
        patient = new Patient();
        address = new Address();
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyChange();
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyChange();
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Bindable
    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String password) {
        this.reEnterPassword = password;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    @Bindable
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ContactNumber> getContactNumbers() {
        return contactNumbers;
    }

    public void setContactNumbers(List<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public void addContactNumbers(String number, int contactNumberTypeId) {
        ContactNumber contactNumber = new ContactNumber();
        contactNumber.setNumber(number);
        contactNumber.setContactNumberTypeId(contactNumberTypeId);
        contactNumbers.add(contactNumber);
    }

    @Bindable
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


}
