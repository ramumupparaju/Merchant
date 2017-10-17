package com.incon.connect.ui.home.asignqrcode.fragment;

import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.ui.BaseView;

import java.util.List;


/**
 * Created by PC on 10/12/2017.
 */

public interface ProductAssignContract {

    interface View extends BaseView {
        void loadModelNumberData(List<ModelSearchResponse> modelSearchResponseList);
        void productAssignQrCode(Object assignQrCodeResponse);
    }

    interface Presenter {
        void doModelSearchApi(String modelNumberToSearch);
    }
}
