package com.incon.connect.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.connect.AppConstants;
import com.incon.connect.utils.DateUtils;
import com.incon.connect.utils.ValidationUtils;

import java.util.Calendar;

import static com.incon.connect.AppConstants.RegistrationValidation.DOB_FUTURE_DATE;
import static com.incon.connect.AppConstants.RegistrationValidation.DOB_PERSON_LIMIT;
import static com.incon.connect.AppConstants.VALIDATION_SUCCESS;

public class UserInfo extends BaseObservable {

    private String name;
    private String phoneNumber;
    private String address;
    private String emailId;
    private String password;
    private String confirmPassword;
    private String genderType;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirthToShow;
    private Address userAddress;

    @Bindable
    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
        notifyChange();
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyChange();
    }

    public Address getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyChange();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        notifyChange();
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyChange();
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Bindable
    public String getDateOfBirthToShow() {
        return dateOfBirthToShow;
    }

    public void setDateOfBirthToShow(String dateOfBirthToShow) {
        this.dateOfBirthToShow = dateOfBirthToShow;
        dob = DateUtils.convertDateToAnotherFormat(dateOfBirthToShow, AppConstants
                .DateFormatterConstants.MM_DD_YYYY, AppConstants.DateFormatterConstants
                .YYYY_MM_DD);
        notifyChange();
    }


    public Pair<String, Integer> validateUserInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 6; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                if (emptyValidation && TextUtils.isEmpty(getName())) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(getPhoneNumber());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                } else if (!phoneEmpty && !ValidationUtils.isPhoneNumberValid(getPhoneNumber())) {
                    return AppConstants.RegistrationValidation.PHONE_MIN_DIGITS;
                }
                break;

            case 2:
                boolean genderTypeEmpty = TextUtils.isEmpty(getGenderType());
                if (emptyValidation && genderTypeEmpty) {
                    return AppConstants.RegistrationValidation.GENDER_REQ;
                }
                break;

            case 3:
                boolean dobEmpty = TextUtils.isEmpty(getDob());
                if (emptyValidation && dobEmpty) {
                    return AppConstants.RegistrationValidation.DOB_REQ;
                } else if (!dobEmpty) {
                    return validateDob();
                }
                break;

            case 4:
                boolean emailEmpty = TextUtils.isEmpty(getEmailId());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                } else if (!emailEmpty && !ValidationUtils.isValidEmail(getEmailId())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 5:
                boolean passwordEmpty = TextUtils.isEmpty(getPassword());
                if (emptyValidation && passwordEmpty) {
                    return AppConstants.RegistrationValidation.PASSWORD_REQ;
                } else if (!passwordEmpty && !ValidationUtils
                        .isPasswordValid(getPassword())) {
                    return AppConstants.RegistrationValidation.PASSWORD_PATTERN_REQ;
                }
                break;

            case 6:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return AppConstants.RegistrationValidation.RE_ENTER_PASSWORD_REQ;
                } else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(getPassword());
                    if (passwordEmpty1 || (!getPassword()
                            .equals(getConfirmPassword()))) {
                        return AppConstants.RegistrationValidation
                                .RE_ENTER_PASSWORD_DOES_NOT_MATCH;
                    }

                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }

    private int validateDob() {
        Calendar dobDate = Calendar.getInstance();
        long dobInMillis = DateUtils.convertStringFormatToMillis(
                getDob(), AppConstants.DateFormatterConstants.YYYY_MM_DD);
        dobDate.setTimeInMillis(dobInMillis);
        // future date check
        if (ValidationUtils.isFutureDate(dobDate)) {
            return DOB_FUTURE_DATE;
        }

        int returnedYear = ValidationUtils.calculateAge(dobDate);
        if (returnedYear < AppConstants.AgeConstants.USER_DOB) {
            return DOB_PERSON_LIMIT;
        }
        return VALIDATION_SUCCESS;
    }
}
