package com.incon.connect.ui.register;


import com.incon.connect.ui.BaseView;

public interface RegistrationContract {

    interface View extends BaseView {
        void navigateToNext();
        void navigateToBack();
    }

    interface Presenter {
    }
}
