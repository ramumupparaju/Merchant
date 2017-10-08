package com.incon.connect.ui.history.fragments;


import com.incon.connect.apimodel.components.history.purchased.PurchasedHistoryResponse;
import com.incon.connect.ui.BaseView;

import java.util.List;

/**
 * Created on 31 May 2017 11:18 AM.
 *
 */
public interface PurchasedContract {

    interface View extends BaseView {
        void loadPurchasedHistory(List<PurchasedHistoryResponse> purchasedHistoryResponseList);
    }

    interface Presenter {
        void purchased(int userId);
    }

}
