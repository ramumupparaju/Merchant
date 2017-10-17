package com.incon.connect.ui.addnewmodel;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.fetchcategorie.FetchCategorie;
import com.incon.connect.databinding.FragmentAddNewModelBinding;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelFragment extends BaseFragment implements AddNewModelContract.View {
    private FragmentAddNewModelBinding binding;
    private View rootView;
    private AddNewModelPresenter addNewModelPresenter;
    private AddNewModel addNewModel;
    private MaterialBetterSpinner typeSpinner;
    private MaterialBetterSpinner divisionSpinner;
    private MaterialBetterSpinner categorySpinner;
    private List<FetchCategorie> fetchCategorieList;

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
            addNewModel = getArguments().getParcelable(BundleConstants.ADD_NEW_MODEL_DATA);
            binding.setAddNewModel(addNewModel);
            binding.setAddNewModelFragment(this);
            rootView = binding.getRoot();
            initViews();
            loadData();
        }
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_add_new_model));
        return rootView;
    }

    private void loadData() {
        loadDivisionSpinnerData();
        loadCategorySpinnerData();
        loadTypeSpinnerData();
    }

    private void loadTypeSpinnerData() {

        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        typeSpinner = binding.spinnerType;
        typeSpinner.setAdapter(arrayAdapter);

    }

    private void loadCategorySpinnerData() {

        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        categorySpinner = binding.spinnerCategory;
        categorySpinner.setAdapter(arrayAdapter);

    }

    private void loadDivisionSpinnerData() {

        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        divisionSpinner = binding.spinnerDivision;
        divisionSpinner.setAdapter(arrayAdapter);
    }


    private void initViews() {
        FetchCategorie fetchCategorie = new FetchCategorie();
        fetchCategorie.setId(fetchCategorie.getId());
        fetchCategorie.setName(fetchCategorie.getName());
    }

    public void onSubmitClick() {
        addNewModelPresenter.addingNewModel(SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE), addNewModel);
    }

    @Override
    public void addNewModel(Object o) {

    }
}
