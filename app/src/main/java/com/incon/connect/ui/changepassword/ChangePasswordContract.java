package com.incon.connect.ui.changepassword;

import com.incon.connect.dto.changepassword.Password;
import com.incon.connect.ui.BaseView;

import java.util.HashMap;

public interface ChangePasswordContract {

    interface View extends BaseView {
        void navigateToLoginPage();
    }

    interface Presenter {
        void setChangePassword(Password password);
        void resetPassword(HashMap<String, String> password);
    }

}
