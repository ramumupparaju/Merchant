package com.incon.connect.ui.qrcodescan;

import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.ui.BaseView;

public interface QrCodeBarcodeContract {

    interface View extends BaseView {
        void navigateToQrCodeBarcodeScree(UserInfoResponse userInfoResponse);
    }

    interface Presenter {
        void getUserScannedInfo(String uuid);
    }

}
