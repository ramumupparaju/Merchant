package com.incon.connect.data.login;


import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.utils.SharedPrefsUtils;

import static com.incon.connect.AppConstants.LoginPrefs.EMAIL_ID;

public class LoginDataManagerImpl implements LoginDataManager, AppConstants.LoginPrefs {

    private static final String TAG = LoginDataManagerImpl.class.getName();

    @Override
    public void saveLoginDataToPrefs(LoginResponse loginResponse) {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();
        sharedPrefsUtils.setStringPreference(EMAIL_ID,
                loginResponse.getData().getEmail());
        sharedPrefsUtils.setIntegerPreference(ACCOUNT_ID,
                loginResponse.getData().getId());
    }
}
