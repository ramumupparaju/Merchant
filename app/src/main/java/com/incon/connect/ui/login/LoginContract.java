package com.incon.connect.ui.login;


import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.ui.BaseView;

import java.util.HashMap;

public interface LoginContract {

    interface View extends BaseView {
        void navigateToHomePage(LoginResponse loginResponse);
    }

    interface Presenter {
        void doLogin(LoginUserData loginUserData);
        void validateOTP(HashMap<String, String> verify);
    }

}
