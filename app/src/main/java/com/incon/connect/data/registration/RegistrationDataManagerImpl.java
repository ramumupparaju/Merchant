package com.incon.connect.data.registration;

import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.components.registration.RegistrationResponse;
import com.incon.connect.utils.SharedPrefsUtils;

public class RegistrationDataManagerImpl implements
        RegistrationDataManager, AppConstants.LoginPrefs {

    @Override
    public void saveLoginDataRoPrefs(RegistrationResponse registrationResponse) {
        // saves authorization/access token to shared prefs
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();
        sharedPrefsUtils.setStringPreference(ACCESS_TOKEN,
                registrationResponse.getData().getToken());
        sharedPrefsUtils.setIntegerPreference(USER_ID,
                registrationResponse.getData().getId());
    }

}
