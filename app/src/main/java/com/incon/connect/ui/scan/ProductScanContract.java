package com.incon.connect.ui.scan;


import com.incon.connect.apimodel.components.qrcodeproduct.ProductInfoResponse;
import com.incon.connect.ui.BaseView;

/**
 * Created on 31 May 2017 11:18 AM.
 *
 */
public interface ProductScanContract {

    interface View extends BaseView {
        void productInfo(ProductInfoResponse productInfoResponse);
    }

    interface Presenter {
        void productInfoUsingQrCode(String qrCode);
    }

}
