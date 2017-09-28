package com.incon.connect.ui.register.fragment;


import com.incon.connect.dto.registration.UserInfo;
import com.incon.connect.ui.BaseView;

/**
 * Created on 08 Jun 2017 6:32 PM.
 *
 */
public class RegistrationUserStoreFragmentContract {

    interface View extends BaseView {
        void navigateToRegistrationActivityNext();
    }

    interface Presenter {
        void registerUserInfo(UserInfo userInfo);
       /* void sendOtp(HashMap<String, String> emailMap);
        void verifyOtp(HashMap<String, String> verifyOtpMap);
        void validateEmailInUse(String email);*/
    }

}
