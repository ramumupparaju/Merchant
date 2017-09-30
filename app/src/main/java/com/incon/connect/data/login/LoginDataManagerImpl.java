package com.incon.connect.data.login;

import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.apimodel.components.login.StoreResponse;
import com.incon.connect.utils.SharedPrefsUtils;

public class LoginDataManagerImpl implements LoginDataManager, AppConstants.LoginPrefs {
    private static final String TAG = LoginDataManagerImpl.class.getName();

    @Override
    public void saveLoginDataToPrefs(LoginResponse loginResponse) {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();

        //Adding user details to preferences
        sharedPrefsUtils.setIntegerPreference(USER_ID,
                loginResponse.getId());
        sharedPrefsUtils.setStringPreference(USER_NAME,
                loginResponse.getName());
        sharedPrefsUtils.setStringPreference(USER_EMAIL_ID,
                loginResponse.getEmail());
        sharedPrefsUtils.setStringPreference(USER_PHONE_NUMBER,
                loginResponse.getMsisdn());
        sharedPrefsUtils.setLongPreference(USER_DOB,
                loginResponse.getDobInMillis());
        sharedPrefsUtils.setStringPreference(USER_GENDER,
                loginResponse.getGender());

        //Adding Store details to preferences
        StoreResponse storeDetails = loginResponse.getStore();
        sharedPrefsUtils.setIntegerPreference(STORE_ID,
                storeDetails.getId());
        sharedPrefsUtils.setStringPreference(STORE_NAME,
                storeDetails.getName());
        sharedPrefsUtils.setStringPreference(STORE_EMAIL_ID,
                storeDetails.getStoreEmail());
        sharedPrefsUtils.setStringPreference(STORE_PHONE_NUMBER,
                storeDetails.getContactNumber());
        sharedPrefsUtils.setStringPreference(STORE_LOGO,
                storeDetails.getLogo());
        sharedPrefsUtils.setStringPreference(STORE_GSTN,
                storeDetails.getGstn());
        sharedPrefsUtils.setStringPreference(STORE_ADDRESS,
                storeDetails.getAddress());
    }
}
