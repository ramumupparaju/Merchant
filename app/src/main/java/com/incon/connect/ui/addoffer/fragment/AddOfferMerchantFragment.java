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
import android.widget.DatePicker;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.FragmentAddOfferMerchantBinding;
import com.incon.connect.dto.addoffer.AddOfferRequest;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.utils.DateUtils;
import com.incon.connect.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.incon.connect.AppConstants.DateFormatterConstants.MM_DD_YYYY;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class AddOfferMerchantFragment extends BaseFragment implements
        AddOfferMerchantContract.View {

    private static final String TAG = AddOfferMerchantFragment.class.getSimpleName();
    private FragmentAddOfferMerchantBinding binding;
    private View rootView;
    private List<AddOfferMerchantFragmentResponse> addOfferMerchantList;
    private AddOfferMerchantPresenter addOfferMerchantPresenter;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private AddOfferRequest addOfferRequest;
    private int merchantId;


    @Override
    protected void initializePresenter() {
        addOfferMerchantPresenter = new AddOfferMerchantPresenter();
        addOfferMerchantPresenter.setView(this);
        setBasePresenter(addOfferMerchantPresenter);
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
            initViews();
            loadData();
        }

        return rootView;
    }

    public void onDateClick() {
        showDatePicker();
    }

    private void showDatePicker() {
        AppUtils.hideSoftKeyboard(getActivity(), getView());
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

      /*  String dateOfBirth = addOfferRequest.getDateOfBirthToShow();
        if (!TextUtils.isEmpty(dateOfBirth)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    dateOfBirth, DateFormatterConstants.MM_DD_YYYY));
        }*/

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                customStyle,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }
    private DatePickerDialog.OnDateSetListener datePickerListener =
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

                    binding.edittextStartOn.setText(strDt);
                    binding.edittextOfferExpires.setText(endDt);
                 //   binding.edittextOfferExpires.setText(endDt);
                    String dobInYYYYMMDD = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.YYYY_MM_DD);
                    //TODO  Have to show Time Picker
/*
                    Pair<String, Integer> startdate = binding.getAddOfferRequest().
                            validateUserInfo((String) binding.edittextAddAnOffer.getTag());
                    Pair<String, Integer> enddate = binding.getAddOfferRequest().
                            validateUserInfo((String) binding.edittextOfferExpires.getTag());*/

                   // updateUiAfterValidation(startdate.first, startdate.second);
                }
            };

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
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_offers));

         merchantId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);

        AddOfferRequest addOfferRequest = new AddOfferRequest();
        addOfferRequest.setCustomerId("19");
        addOfferRequest.setMerchantId("2");
        addOfferRequest.setBrandId("1");
        addOfferRequest.setCategoryId("5");
        addOfferRequest.setModelNumber("MODEL3321");
        addOfferRequest.setOffer("20");
        addOfferRequest.setProductId("34");
        addOfferRequest.setPurchaseId("2");
        addOfferRequest.setFromDate("2017-10-07T20:02:03.725Z");
        addOfferRequest.setToDate("2017-10-27T20:02:03.725Z");
        addOfferMerchantPresenter.addOffer(merchantId);

    }


    @Override
    public void loadAddOfferMerchant(int merchantId) {

    }
}
