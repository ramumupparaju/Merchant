package com.incon.connect.ui.login;


import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.dto.login.User;
import com.incon.connect.ui.BaseView;

public interface LoginContract {

    interface View extends BaseView {
        void navigateToHomePage(LoginResponse loginResponse);
        void navigateToChangePassword();
    }

    interface Presenter {
        void doLogin(User user);
    }

}
