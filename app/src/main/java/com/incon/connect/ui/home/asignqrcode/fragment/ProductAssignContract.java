package com.incon.connect.ui.home.asignqrcode.fragment;

import com.incon.connect.ui.BaseView;


/**
 * Created by PC on 10/12/2017.
 */

public interface ProductAssignContract {

    interface View extends BaseView {

        void productAssignQrCode(Object assignQrCodeResponse);
    }

    interface Presenter {
    }
}
