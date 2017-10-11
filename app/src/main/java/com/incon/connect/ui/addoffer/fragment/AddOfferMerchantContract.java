package com.incon.connect.ui.addoffer.fragment;

import com.incon.connect.ui.BaseView;

/**
 * Created by PC on 10/8/2017.
 */

public interface AddOfferMerchantContract {
    interface View extends BaseView {
        void loadAddOfferMerchant(int merchantId);
    }
    interface Presenter {

    }
}
