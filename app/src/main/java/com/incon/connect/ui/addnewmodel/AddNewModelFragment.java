package com.incon.connect.ui.addnewmodel;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.fetchcategorie.Brand;
import com.incon.connect.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.connect.apimodel.components.fetchcategorie.Division;
import com.incon.connect.databinding.FragmentAddNewModelBinding;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.utils.SharedPrefsUtils;

import java.util.List;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelFragment extends BaseFragment implements AddNewModelContract.View {
    private FragmentAddNewModelBinding binding;
    private View rootView;
    private AddNewModelPresenter addNewModelPresenter;
    private AddNewModel addNewModel;
    private List<FetchCategories> fetchCategorieList;
    private int categorySelectedPos, divisionSelectedPos;

    @Override
    protected void initializePresenter() {
        addNewModelPresenter = new AddNewModelPresenter();
        addNewModelPresenter.setView(this);
        setBasePresenter(addNewModelPresenter);
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_add_new_model));
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
            addNewModelPresenter.getCategories(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.STORE_ID, DEFAULT_VALUE));
        }
        setTitle();
        return rootView;
    }

    private void loadCategorySpinnerData() {
        String[] stringCategoryList = new String[fetchCategorieList.size()];
        for (int i = 0; i < fetchCategorieList.size(); i++) {
            stringCategoryList[i] = fetchCategorieList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, stringCategoryList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerCategory.setAdapter(arrayAdapter);
        binding.spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (categorySelectedPos != position) {
                    FetchCategories fetchCategories = fetchCategorieList.get(position);
                    addNewModel.setCategoryId(String.valueOf(fetchCategories.getId()));
                    loadDivisionSpinnerData(fetchCategories.getDivisions());
                    binding.spinnerDivision.setText("");
                    categorySelectedPos = position;
                    binding.spinnerBrand.setVisibility(View.GONE);
                }

            }
        });
    }

    private void loadDivisionSpinnerData(List<Division> divisions) {

       /* if (divisions.size() == 0) {
            binding.spinnerDivision.setVisibility(View.GONE);
            return;
        }*/

        binding.spinnerDivision.setVisibility(View.VISIBLE);
        String[] stringDivisionList = new String[divisions.size()];
        for (int i = 0; i < divisions.size(); i++) {
            stringDivisionList[i] = divisions.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, stringDivisionList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerDivision.setAdapter(arrayAdapter);
        binding.spinnerDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (divisionSelectedPos != position) {
                    divisionSelectedPos = position;
                    FetchCategories fetchCategories = fetchCategorieList.get(categorySelectedPos);
                    Division divisions1 = fetchCategories.getDivisions().get(divisionSelectedPos);
                    addNewModel.setDivisionId(String.valueOf((divisions1.getId())));
                    loadBrandSpinnerData(divisions1.getBrands());
                    binding.spinnerBrand.setText("");
                }
            }
        });
    }

    private void loadBrandSpinnerData(List<Brand> brandList) {

        if (brandList.size() == 0) {
            binding.spinnerBrand.setVisibility(View.GONE);
            return;
        }
        binding.spinnerBrand.setVisibility(View.VISIBLE);
        String[] stringDivisionList = new String[brandList.size()];
        for (int i = 0; i < brandList.size(); i++) {
            stringDivisionList[i] = brandList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.view_spinner, stringDivisionList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerBrand.setAdapter(arrayAdapter);
        binding.spinnerBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO have to select brand id
            }
        });
    }


    private void initViews() {
    }

    public void onSubmitClick() {
        addNewModelPresenter.addingNewModel(SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE), addNewModel);
    }

    @Override
    public void addNewModel(Object o) {
        getActivity().onBackPressed();
    }

    @Override
    public void loadCategoriesList(List<FetchCategories> categoriesList) {
        fetchCategorieList = categoriesList;
        loadCategorySpinnerData();
    }
}
