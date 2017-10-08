package com.incon.connect.ui.scan;


import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.ui.BaseView;

/**
 * Created on 31 May 2017 11:18 AM.
 *
 */
public interface ScanTabContract {

    interface View extends BaseView {
        void userInfo(UserInfoResponse userInfoResponse);
    }

    interface Presenter {
        void userInfoUsingPhoneNumber(String phoneNumber);
        void userInfoUsingQrCode(String qrCode);
    }

}
