package com.incon.connect.ui;


import com.incon.connect.apimodel.Location.LocationPostData;

public interface RegistrationMapContract {

    interface View extends BaseView {
        void moveMarkerToThisLocation(LocationPostData locationResponse);
    }

    interface Presenter {
        void doPostalCode(String loginUserData);
    }

}
