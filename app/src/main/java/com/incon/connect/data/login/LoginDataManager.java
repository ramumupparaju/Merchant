package com.incon.connect.data.login;


import com.incon.connect.apimodel.components.login.LoginResponse;

public interface LoginDataManager {

    void saveLoginDataToPrefs(LoginResponse loginResponse);
}
