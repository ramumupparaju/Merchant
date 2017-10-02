package com.incon.connect.ui;


import com.incon.connect.dto.Location.LocationPostData;

public interface RegistrationMapContract {

    interface View extends BaseView {
        void navigateToHomePage(LocationPostData loginResponse);
    }

    interface Presenter {
        void doPostalCode(String loginUserData);
    }

}
