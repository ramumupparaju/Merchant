package com.incon.connect.ui.addoffer.fragment;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.apimodel.components.fetchcategorie.Brand;
import com.incon.connect.apimodel.components.fetchcategorie.Division;
import com.incon.connect.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.connect.callbacks.DateDialogCallback;
import com.incon.connect.custom.view.CustomDateListener;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.FragmentAddOfferMerchantBinding;
import com.incon.connect.dto.addoffer.AddOfferRequest;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.utils.DateUtils;
import com.incon.connect.utils.SharedPrefsUtils;
import com.incon.connect.utils.ValidationUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class AddOfferMerchantFragment extends BaseFragment implements
        AddOfferMerchantContract.View {

    private static final String TAG = AddOfferMerchantFragment.class.getSimpleName();
    private FragmentAddOfferMerchantBinding binding;
    private View rootView;
    private List<AddOfferMerchantFragmentResponse> addOfferMerchantList;
    private List<FetchCategories> fetchCategorieList;
    private AddOfferMerchantPresenter addOfferMerchantPresenter;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private AddOfferRequest addOfferRequest;
    private int merchantId;
    private int categorySelectedPos, divisionSelectedPos, brandSelectedPos;


    @Override
    protected void initializePresenter() {
        addOfferMerchantPresenter = new AddOfferMerchantPresenter();
        addOfferMerchantPresenter.setView(this);
        setBasePresenter(addOfferMerchantPresenter);
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_offers));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_add_offer_merchant, container, false);
            addOfferRequest = new AddOfferRequest();
            binding.setAddOfferRequest(addOfferRequest);
            binding.setAddOfferMerchantFragment(this);
            rootView = binding.getRoot();
            addOfferMerchantPresenter.getCategories(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.STORE_ID, DEFAULT_VALUE));
            initViews();
            loadData();
        }
        setTitle();
        return rootView;
    }

    public void onDateClick(View view) {
        int dialogType = DateDialogConstants.ADD_OFFER_START_DATE;
        switch (view.getId()) {
            case R.id.view_date_expire:
                dialogType = DateDialogConstants.ADD_OFFER_END_DATE;
                break;
            case R.id.view_offer_scan_start_on:
                dialogType = DateDialogConstants.ADD_OFFER_SCAN_START_DATE;
                break;
            case R.id.view_scan_expire:
                dialogType = DateDialogConstants.ADD_OFFER_SCAN_END_DATE;
                break;
            default:
                //do nothing
                break;
        }
        showDatePicker(Calendar.getInstance(), dialogType);
    }

    public void onSubmitClick() {
        AddOfferRequest addOfferRequest = binding.getAddOfferRequest();
        addOfferMerchantPresenter.addOffer(addOfferRequest);
    }

    private void showDatePicker(Calendar calendar, int dateDialogType) {
        AppUtils.hideSoftKeyboard(getActivity(), getView());
        CustomDateListener customDateDialog = new CustomDateListener(dateDialogType,
                dateDialogCallback);
        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                customStyle,
                customDateDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }

    DateDialogCallback dateDialogCallback = new DateDialogCallback() {
        @Override
        public void onDateSet(int dialogType, DatePicker view, int year,
                              int month, int dayOfMonth) {
            Calendar selectedDateTime = Calendar.getInstance();
            selectedDateTime.set(year, month, dayOfMonth);
            String formattedDate = DateUtils.convertMillsToDateString(DateFormatterConstants
                    .DD_E_MMMM_YYYY, selectedDateTime.getTimeInMillis());
            switch (dialogType) {
                case DateDialogConstants.ADD_OFFER_START_DATE:
                    binding.edittextStartOn.setText(formattedDate);
                    break;
                case DateDialogConstants.ADD_OFFER_END_DATE:
                    if (!ValidationUtils.isFutureDate(selectedDateTime)) {
                        showErrorMessage(getString(R.string.error_past_date));
                        return;
                    }
                    binding.edittextOfferExpires.setText(formattedDate);
                    break;
                case DateDialogConstants.ADD_OFFER_SCAN_START_DATE:
                    binding.edittextScanStartOn.setText(formattedDate);
                    break;
                case DateDialogConstants.ADD_OFFER_SCAN_END_DATE:
                    if (ValidationUtils.isFutureDate(selectedDateTime)) {
                        showErrorMessage(getString(R.string.error_dob_futuredate));
                        return;
                    }
                    binding.edittextScanExpires.setText(formattedDate);
                    break;
                default:
                    //do nothing
                    break;
            }
        }
    };

    /*
    private CustomDateListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay);

                    SimpleDateFormat simpleDate = new SimpleDateFormat(MM_DD_YYYY,
                            Locale.getDefault());
                    String strDt = simpleDate.format(selectedDateTime.getTime());
                    String endDt = simpleDate.format(selectedDateTime.getTime());
                    String scanStartDt = simpleDate.format(selectedDateTime.getTime());
                    String scanEdndDt = simpleDate.format(selectedDateTime.getTime());

                    binding.edittextStartOn.setText(strDt);
                    binding.edittextOfferExpires.setText(endDt);
                    binding.edittextScanStartOn.setText(scanStartDt);
                    binding.edittextScanExpires.setText(scanEdndDt);

                    String dobInYYYYMMDD = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.YYYY_MM_DD);
                    //TODO  Have to show Time Picker
*//*
                    Pair<String, Integer> startdate = binding.getAddOfferRequest().
                            validateUserInfo((String) binding.edittextAddAnOffer.getTag());
                    Pair<String, Integer> enddate = binding.getAddOfferRequest().
                            validateUserInfo((String) binding.edittextOfferExpires.getTag());*//*

                    // updateUiAfterValidation(startdate.first, startdate.second);
                }
            };*/

    private void loadData() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
    }

    private void updateUiAfterValidation(String tag, int validationId) {

        if (tag == null) {
            return;
        }

        View viewByTag = binding.getRoot().findViewWithTag(tag);
        setFieldError(viewByTag, validationId);
    }

    private void setFieldError(View view, int validationId) {

        if (view instanceof TextInputEditText) {
            ((CustomTextInputLayout) view.getParent().getParent())
                    .setError(validationId == VALIDATION_SUCCESS ? null
                            : errorMap.get(validationId));
        } else {
            ((MaterialBetterSpinner) view).setError(validationId == VALIDATION_SUCCESS ? null
                    : errorMap.get(validationId));
        }
    }

    private void initViews() {

        merchantId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);

        FetchCategories fetchCategorie = new FetchCategories();
        fetchCategorie.setId(fetchCategorie.getId());
        fetchCategorie.setName(fetchCategorie.getName());
    }

    @Override
    public void loadAddOfferMerchant(AddOfferMerchantFragmentResponse merchantId) {
        AppUtils.showSnackBar(rootView, merchantId.getModelNumber());
    }

    public void loadCategoriesList(List<FetchCategories> categoriesList) {
        fetchCategorieList = categoriesList;
        loadCategorySpinnerData();
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
                    addOfferRequest.setCategoryId(String.valueOf(fetchCategories.getId()));
                    loadDivisionSpinnerData(fetchCategories.getDivisions());
                    binding.spinnerDivision.setText("");
                    categorySelectedPos = position;
                    binding.spinnerBrand.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadDivisionSpinnerData(List<Division> divisions) {

        if (divisions.size() == 0) {
            binding.spinnerDivision.setVisibility(View.GONE);
            return;
        }

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
                divisionSelectedPos = position;
                FetchCategories fetchCategories = fetchCategorieList.get(categorySelectedPos);
                Division divisions1 = fetchCategories.getDivisions().get(divisionSelectedPos);
                addOfferRequest.setDivisionId(String.valueOf((divisions1.getId())));
                loadBrandSpinnerData(divisions1.getBrands());
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
}
