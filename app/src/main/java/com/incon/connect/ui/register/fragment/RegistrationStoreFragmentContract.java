package com.incon.connect.ui.register.fragment;


import com.incon.connect.dto.registration.Register;
import com.incon.connect.ui.BaseView;

/**
 * Created on 08 Jun 2017 6:32 PM.
 *
 */
public class RegistrationStoreFragmentContract {

    interface View extends BaseView {
        void navigateToRegistrationActivityNext();
        void navigateToHomeScreen();
    }

    interface Presenter {
        void register(Register registrationBody);

       /* void sendOtp(HashMap<String, String> emailMap);
        void verifyOtp(HashMap<String, String> verifyOtpMap);
        void validateEmailInUse(String email);*/
    }

}
