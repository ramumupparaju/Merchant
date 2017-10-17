package com.incon.connect.ui.register;


import com.incon.connect.ui.BaseView;

public interface RegistrationContract {

    interface View extends BaseView {
        void navigateToNext();
        void navigateToBack();
        //defaults data available true, else false
        void startRegistration(boolean isDataAvailable);
    }

    interface Presenter {
        void defaultsApi();
    }
}
