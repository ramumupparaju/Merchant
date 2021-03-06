package com.incon.connect.ui.changepassword;

import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.ui.BaseView;

import java.util.HashMap;

public interface ChangePasswordContract {

    interface View extends BaseView {
        void navigateToLoginPage(LoginResponse loginResponse);
    }

    interface Presenter {
        void changePassword(HashMap<String, String> password);
        void saveUserData(LoginResponse loginResponse);
    }

}
