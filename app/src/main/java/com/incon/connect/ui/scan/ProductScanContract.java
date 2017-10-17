package com.incon.connect.ui.scan;


import com.incon.connect.ui.BaseView;

/**
 * Created on 31 May 2017 11:18 AM.
 *
 */
public interface ProductScanContract {

    interface View extends BaseView {
        void productInfo(Object productInfoResponse);
    }

    interface Presenter {
        void productInfoUsingQrCode(String qrCode);
    }

}
