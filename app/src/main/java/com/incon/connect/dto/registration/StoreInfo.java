package com.incon.connect.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.utils.ValidationUtils;

import static com.incon.connect.AppConstants.VALIDATION_SUCCESS;

public class StoreInfo extends BaseObservable {

    private String storeDetails;
    private String storeName;
    private String storeEmail;
    private String storeCategory;
    private String storeLocation;
    private String storeLogo;
    private String storeAddress;
    private String storeGSTN;
    private String storePhoneNumber;

    @Bindable
    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    @Bindable
    public String getStoreDetails() {
        return storeDetails;
    }

    public void setStoreDetails(String storeDetails) {
        this.storeDetails = storeDetails;
    }

    @Bindable
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Bindable
    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Bindable
    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    @Bindable
    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    @Bindable
    public String getStoreGSTN() {
        return storeGSTN;
    }

    public void setStoreGSTN(String storeGSTN) {
        this.storeGSTN = storeGSTN;
    }

    @Bindable
    public String getStorePhoneNumber() {
        return storePhoneNumber;
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
        this.storePhoneNumber = storePhoneNumber;
    }


    public Pair<String, Integer> validateStoreInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 5; i++) {
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
                if (emptyValidation && TextUtils.isEmpty(getStoreName())) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(getStorePhoneNumber());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                } else if (!phoneEmpty && !ValidationUtils.isPhoneNumberValid(
                        getStorePhoneNumber())) {
                    return AppConstants.RegistrationValidation.PHONE_MIN_DIGITS;
                }
                break;

            case 2:
                boolean storeCategory = TextUtils.isEmpty(getStoreCategory());
                if (emptyValidation && storeCategory) {
                    return AppConstants.RegistrationValidation.CATEGORY_REQ;
                }
                break;

            case 3:
                boolean storeAddress = TextUtils.isEmpty(getStoreAddress());
                if (emptyValidation && storeAddress) {
                    return AppConstants.RegistrationValidation.ADDRESS_REQ;
                }
                break;

            case 4:
                boolean emailEmpty = TextUtils.isEmpty(getStoreEmail());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                } else if (!emailEmpty && !ValidationUtils.isValidEmail(getStoreEmail())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 5:
                boolean storeGstn = TextUtils.isEmpty(getStoreGSTN());
                if (emptyValidation && storeGstn) {
                    return AppConstants.RegistrationValidation.GSTN_REQ;
                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }

}
