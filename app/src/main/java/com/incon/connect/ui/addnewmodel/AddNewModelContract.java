package com.incon.connect.ui.addnewmodel;

import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BaseView;

/**
 * Created by PC on 10/4/2017.
 */

public interface AddNewModelContract {

    interface View extends BaseView {
        void addNewModel(Object o);
    }

    interface Presenter {
        void addingNewModel(int merchantId, AddNewModel addNewModel);
    }
}
