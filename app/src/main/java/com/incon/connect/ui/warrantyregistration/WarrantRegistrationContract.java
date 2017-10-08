package com.incon.connect.ui.warrantyregistration;

import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 9/26/2017.
 */
public interface WarrantRegistrationContract {
    interface View extends BaseView {
        void loadModelNumberData(List<ModelSearchResponse> modelSearchResponseList);
    }

    interface Presenter {
        void doModelSearchApi(String modelNumberToSearch);
    }
}
