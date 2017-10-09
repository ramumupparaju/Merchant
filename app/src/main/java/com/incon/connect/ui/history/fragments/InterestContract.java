package com.incon.connect.ui.history.fragments;

import com.incon.connect.apimodel.components.history.purchased.InterestHistoryResponse;
import com.incon.connect.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 10/2/2017.
 */

public interface InterestContract {

    interface View extends BaseView {
        void loadInterestHistory(List<InterestHistoryResponse> interestHistoryResponseList);
    }

    interface Presenter {
    }

}
