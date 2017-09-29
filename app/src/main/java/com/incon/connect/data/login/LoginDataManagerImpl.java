package com.incon.connect.data.login;


import com.incon.connect.AppConstants;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.utils.SharedPrefsUtils;

public class LoginDataManagerImpl implements LoginDataManager, AppConstants.LoginPrefs {

    private static final String TAG = LoginDataManagerImpl.class.getName();

    @Override
    public void saveLoginDataToPrefs(LoginResponse loginResponse) {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();
        sharedPrefsUtils.setStringPreference(EMAIL_ID,
                loginResponse.getEmail());
        sharedPrefsUtils.setIntegerPreference(ACCOUNT_ID,
                loginResponse.getId());
    }
}
