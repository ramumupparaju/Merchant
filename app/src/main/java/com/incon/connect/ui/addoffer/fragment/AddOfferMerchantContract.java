package com.incon.connect.ui.addoffer.fragment;

import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 10/8/2017.
 */

public interface AddOfferMerchantContract {
    interface View extends BaseView {
        void loadAddOfferMerchant(List<AddOfferMerchantFragmentResponse>
                                          addOfferMerchantFragmentResponsesList);
    }
    interface Presenter {

    }
}
