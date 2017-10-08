package com.incon.connect.ui.qrcodescan;

import com.incon.connect.ui.BaseView;

public interface QrCodeBarcodeContract {

    interface View extends BaseView {
        void navigateToPreviousScreen(String qrCode);
    }

    interface Presenter {
        //Do nothing
    }

}
