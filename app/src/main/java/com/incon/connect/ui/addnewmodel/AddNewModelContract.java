package com.incon.connect.ui.addnewmodel;

import com.incon.connect.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 10/4/2017.
 */

public interface AddNewModelContract {

    interface View extends BaseView {
        void addNewModel(Object o);
        void loadCategoriesList(List<FetchCategories> categoriesList);
    }

    interface Presenter {
        void getCategories(int merchantId);
        void addingNewModel(int merchantId, AddNewModel addNewModel);
    }
}
