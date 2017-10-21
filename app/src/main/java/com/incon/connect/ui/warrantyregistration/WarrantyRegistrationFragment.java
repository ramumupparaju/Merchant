package com.incon.connect.ui.warrantyregistration;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.search.Category;
import com.incon.connect.apimodel.components.search.Division;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.callbacks.AlertDialogCallback;
import com.incon.connect.callbacks.TextAlertDialogCallback;
import com.incon.connect.custom.view.AppAlertDialog;
import com.incon.connect.custom.view.AppOtpDialog;
import com.incon.connect.custom.view.CustomAutoCompleteView;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addnewmodel.AddNewModelFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.ui.warrantyregistration.adapter.ModelSearchArrayAdapter;
import com.incon.connect.utils.SharedPrefsUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 9/28/2017.
 */
public class WarrantyRegistrationFragment extends BaseFragment implements
        WarrantRegistrationContract.View, View.OnClickListener {

    private View rootView;
    private FragmentWarrantyRegistrationBinding binding;
    private WarrantRegistrationPresenter warrantRegistrationPresenter;
    private DisposableObserver<TextViewAfterTextChangeEvent> observer;
    private WarrantyRegistration warrantyRegistration;

    private ModelSearchArrayAdapter modelNumberAdapter;
    private List<ModelSearchResponse> modelSearchResponseList;
    private String selectedModelNumber;
    private int selectedPosition;
    boolean isOtpVerified;
    private AppAlertDialog warrantyStatusDialog;
    private AppOtpDialog userOtpDialog;
    private String enteredOtp;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private KeyListener listener;


    @Override
    protected void initializePresenter() {
        warrantRegistrationPresenter = new WarrantRegistrationPresenter();
        warrantRegistrationPresenter.setView(this);
        setBasePresenter(warrantRegistrationPresenter);
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_warranty_register));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            binding.setFragment(this);
            warrantyRegistration = getArguments().getParcelable(BundleConstants.WARRANTY_DATA);
            warrantyRegistration.setMerchantId(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE));
            binding.setWarrantyRegistration(warrantyRegistration);
            rootView = binding.getRoot();

            initViews();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        listener = binding.edittextModelNumber.getKeyListener();
        if (warrantyRegistration.isFromProductScan()) {
            binding.edittextModelNumber.setKeyListener(null);
            showViews(true);
        } else {
            binding.edittextModelNumber.setKeyListener(listener);
            initializeModelNumberAdapter(new ArrayList<ModelSearchResponse>());
        }
        initializeModelNumberAdapter(new ArrayList<ModelSearchResponse>());
        binding.imBarcodeSerialNo.setOnClickListener(this);
        binding.imBarcodeBatch.setOnClickListener(this);
        loadValidationErrors();
        setFocusForViews();
    }

    private void initializeModelNumberAdapter(List<ModelSearchResponse>
                                                      modelNumberList) {
        this.modelSearchResponseList = modelNumberList;
        modelNumberAdapter = new ModelSearchArrayAdapter(getContext(),
                modelNumberList);
        binding.edittextModelNumber.setAdapter(modelNumberAdapter);
        setObservableForModelNumber(binding.edittextModelNumber);

        binding.edittextModelNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                selectedPosition = pos;
                ModelSearchResponse modelSearchResponse = modelSearchResponseList.get(pos);
                warrantyRegistration.setProductId(modelSearchResponse.getId());
                Category category = modelSearchResponse.getCategory();
                warrantyRegistration.setCategoryId(category.getId());
                warrantyRegistration.setCategoryName(String.valueOf(category.getName()));
                Division division = modelSearchResponse.getDivision();
                warrantyRegistration.setDivisionId(division.getId());
                warrantyRegistration.setDivisionName(String.valueOf(division.getName()));
                selectedModelNumber = modelSearchResponseList.get(
                        selectedPosition).getModelNumber();
                warrantyRegistration.setPrice(String.valueOf(modelSearchResponse.getPrice()));
                warrantyRegistration.setDescription(modelSearchResponse.getInformation());
                AppUtils.hideSoftKeyboard(getActivity(), rootView);
                showViews(true);
            }
        });
    }

    private void showViews(boolean isShow) {
        binding.inputLayoutBatchNo.setVisibility(View.VISIBLE);
        binding.inputLayoutPrice.setVisibility(View.VISIBLE);
        binding.inputLayoutSerialNo.setVisibility(View.VISIBLE);
        binding.inputLayoutInvoicenumber.setVisibility(View.VISIBLE);
        binding.imBarcodeSerialNo.setVisibility(View.VISIBLE);
        binding.imBarcodeBatch.setVisibility(View.VISIBLE);
        binding.inputLayoutDescription.setVisibility(View.VISIBLE);
        binding.productStatus.setVisibility(View.VISIBLE);
    }

    private void setObservableForModelNumber(CustomAutoCompleteView edittextModelNumber) {
        if (observer != null) {
            observer.dispose();
        }
        observer = new DisposableObserver<TextViewAfterTextChangeEvent>() {

            @Override
            public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                String modelNumberString = textViewAfterTextChangeEvent.editable()
                        .toString();
                if ((TextUtils.isEmpty(selectedModelNumber) || !selectedModelNumber.equals(
                        modelNumberString))) {
                    if (modelNumberString.length() > WarrantyRegistrationConstants
                            .MINIMUM_MODELNUMBER_TO_SEARCH) {
                        warrantRegistrationPresenter.doModelSearchApi(modelNumberString);
                        selectedModelNumber = modelNumberString;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
        RxTextView.afterTextChangeEvents(edittextModelNumber)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void loadModelNumberData(List<ModelSearchResponse> modelSearchResponseList) {
        if (modelSearchResponseList == null) {
            modelSearchResponseList = new ArrayList<>();
        }
        initializeModelNumberAdapter(modelSearchResponseList);
        binding.edittextModelNumber.showDropDown();
        if (modelSearchResponseList.size() == 0) {
            showErrorMessage(getString(R.string.error_model_message));
        }
    }

    @Override
    public void warrantyRegistered(Object warrantyRegisteredResponse) {
        showWarrantySuccessfulRegistionDialog();
    }

    @Override
    public void validateUserOTP() {
        dismissDialog(userOtpDialog);
        isOtpVerified = true;
        callWarrantyApi();
    }


    public void onNewModelClick() {
        ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                AddNewModelFragment.class, null);
    }

    public void onSubmitClick() {
        if (validateFields()) {
            if (isOtpVerified) {
                callWarrantyApi();
            } else {
                showOtpDialog();
            }
        }
    }

    private void callWarrantyApi() {
        if (binding.productStatus.isChecked()) {
            warrantyRegistration.setStatus(WarrantyRegistrationConstants.STATUS_PRODUCT_DELIVERED);
        } else {
            warrantyRegistration.setStatus(
                    WarrantyRegistrationConstants.STATUS_PRODUCT_NOT_DELIVERED);
        }
        warrantRegistrationPresenter.doWarrantyRegistrationApi(warrantyRegistration);
    }

    private void showOtpDialog() {
        final String phoneNumber = warrantyRegistration.getMobileNumber();
        userOtpDialog = new AppOtpDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String otpString) {
                        enteredOtp = otpString;
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        AppUtils.hideSoftKeyboard(getActivity(), rootView);
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                if (TextUtils.isEmpty(enteredOtp)) {
                                    showErrorMessage(getString(R.string.error_otp_req));
                                    return;
                                }
                                HashMap<String, String> verifyOTP = new HashMap<>();
                                verifyOTP.put(ApiRequestKeyConstants.BODY_MOBILE_NUMBER,
                                        phoneNumber);
                                verifyOTP.put(ApiRequestKeyConstants.BODY_OTP, enteredOtp);
                                warrantRegistrationPresenter.validateUserOTP(verifyOTP);
                                break;
                            case AlertDialogCallback.CANCEL:
                                userOtpDialog.dismiss();
                                break;
                            case TextAlertDialogCallback.RESEND_OTP:
                                warrantRegistrationPresenter.resendUserOTP(phoneNumber);
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_verify_title, phoneNumber))
                .build();
        userOtpDialog.showDialog();
    }

    private void showWarrantySuccessfulRegistionDialog() {
        warrantyStatusDialog = new AppAlertDialog.AlertDialogBuilder(getActivity(), new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                            case AlertDialogCallback.CANCEL:
                                getActivity().onBackPressed();
                                warrantyStatusDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_warranty_registered_successfully))
                .button1Text(getString(R.string.action_ok))
                .build();
        warrantyStatusDialog.showDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog(warrantyStatusDialog);
        dismissDialog(userOtpDialog);
        if (observer != null) {
            observer.dispose();
        }
        warrantRegistrationPresenter.disposeAll();
    }

    private void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_barcode_serial_no: {
                Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
                intent.putExtra(IntentConstants.SCANNED_TITLE, getString(
                        R.string.title_warranty_serial_barcode));
                startActivityForResult(intent, RequestCodes.SERIAL_NO_SCAN);
            }
            break;
            case R.id.im_barcode_batch: {
                Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
                intent.putExtra(IntentConstants.SCANNED_TITLE, getString(
                        R.string.title_warranty_batch_barcode));
                startActivityForResult(intent, RequestCodes.BATCH_NO_SCAN);
            }
            break;
            default:
                // do nothing
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.SERIAL_NO_SCAN:
                    if (data != null) {
                        binding.edittextSerialNo.setText(
                                data.getStringExtra(IntentConstants.SCANNED_CODE));
                    }
                    break;
                case RequestCodes.BATCH_NO_SCAN:
                    if (data != null) {
                        binding.edittextBatchNo.setText(
                                data.getStringExtra(IntentConstants.SCANNED_CODE));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(WarrantyregistationValidation.MODEL, getString(R.string.error_product_model));
        errorMap.put(WarrantyregistationValidation.SERIAL_NO, getString(
                R.string.error_product_serial));
        errorMap.put(WarrantyregistationValidation.BATCH_NO, getString(
                R.string.error_product_batch));
        errorMap.put(WarrantyregistationValidation.PRICE, getString(
                R.string.error_product_price));
        errorMap.put(WarrantyregistationValidation.INVOICENUMBER, getString(R.string
                .error_product_invoice_number));

    }

    private void setFocusForViews() {
        binding.edittextModelNumber.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextSerialNo.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextBatchNo.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextPrice.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextInvoicenumber.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = binding.getWarrantyRegistration().
                        validateWarrantyRegistration((String) fieldId);
                if (!hasFocus) {
                    if (view instanceof TextInputEditText) {
                        TextInputEditText textInputEditText = (TextInputEditText) view;
                        textInputEditText.setText(textInputEditText.getText().toString().trim());
                    }
                    updateUiAfterValidation(validation.first, validation.second);
                }
            }
        }
    };


    private boolean validateFields() {
        binding.inputLayoutModelNumber.setError(null);
        binding.inputLayoutSerialNo.setError(null);
        binding.inputLayoutBatchNo.setError(null);
        binding.inputLayoutPrice.setError(null);
        binding.inputLayoutInvoicenumber.setError(null);

        Pair<String, Integer> validation = binding.getWarrantyRegistration().
                validateWarrantyRegistration(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
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
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }

}
