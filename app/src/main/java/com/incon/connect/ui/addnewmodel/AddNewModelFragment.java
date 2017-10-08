package com.incon.connect.ui.addnewmodel;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentAddNewModelBinding;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.utils.SharedPrefsUtils;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelFragment extends BaseFragment implements AddNewModelContract.View {
    private FragmentAddNewModelBinding binding;
    private View rootView;
    private AddNewModelPresenter addNewModelPresenter;
    private AddNewModel addNewModel;

    @Override
    protected void initializePresenter() {
        addNewModelPresenter = new AddNewModelPresenter();
        addNewModelPresenter.setView(this);
        setBasePresenter(addNewModelPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_add_new_model, container, false);
            addNewModel = new AddNewModel();
            binding.setAddNewModel(addNewModel);
            binding.setAddNewModelFragment(this);
            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    private void initViews() {
    }

    public void onSubmitClick() {
        addNewModelPresenter.addingNewModel(SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE), addNewModel);
    }

    @Override
    public void addNewModel(Object o) {

    }
}
