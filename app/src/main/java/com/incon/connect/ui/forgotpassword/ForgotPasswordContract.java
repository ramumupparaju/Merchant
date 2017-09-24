package com.incon.connect.ui.forgotpassword;

import com.incon.connect.ui.BaseView;

import java.util.HashMap;

public interface ForgotPasswordContract {

    interface View extends BaseView {
        void navigateToResetPromtPage();
        boolean validateEmail();
    }

    interface Presenter {
        void forgotPassword(HashMap<String, String> email);
    }

}
