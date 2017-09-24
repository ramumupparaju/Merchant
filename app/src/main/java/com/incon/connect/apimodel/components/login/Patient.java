package com.incon.connect.apimodel.components.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.AppConstants;
import com.incon.connect.utils.DateUtils;

public class Patient {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("externalId")
    @Expose
    private String externalId;
    @SerializedName("otherMeds")
    @Expose
    private String otherMeds;
    @SerializedName("knownTriggers")
    @Expose
    private String knownTriggers;
    @SerializedName("activities")
    @Expose
    private String activities;
    @SerializedName("controllerMeds")
    @Expose
    private String controllerMeds;
    @SerializedName("rescueMeds")
    @Expose
    private String rescueMeds;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("gender_id")
    @Expose
    private Integer genderId;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("account_patients")
    @Expose
    private AccountPatients accountPatients;
    @SerializedName("gender")
    @Expose
    private Gender gender;
    @SerializedName("sensor")
    @Expose
    private Sensor sensor;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        dob = DateUtils.convertDateToAnotherFormat(dateOfBirth, AppConstants
                .DateFormatterConstants.MM_DD_YYYY, AppConstants.DateFormatterConstants
                .YYYY_MM_DD);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getOtherMeds() {
        return otherMeds;
    }

    public void setOtherMeds(String otherMeds) {
        this.otherMeds = otherMeds;
    }

    public String getKnownTriggers() {
        return knownTriggers;
    }

    public void setKnownTriggers(String knownTriggers) {
        this.knownTriggers = knownTriggers;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getControllerMeds() {
        return controllerMeds;
    }

    public void setControllerMeds(String controllerMeds) {
        this.controllerMeds = controllerMeds;
    }

    public String getRescueMeds() {
        return rescueMeds;
    }

    public void setRescueMeds(String rescueMeds) {
        this.rescueMeds = rescueMeds;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public AccountPatients getAccountPatients() {
        return accountPatients;
    }

    public void setAccountPatients(AccountPatients accountPatients) {
        this.accountPatients = accountPatients;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Sensor getSensor() {
        return sensor;
    }
}