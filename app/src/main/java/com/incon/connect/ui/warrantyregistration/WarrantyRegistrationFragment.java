package com.incon.connect.ui.warrantyregistration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.callbacks.AlertDialogCallback;
import com.incon.connect.callbacks.TextAlertDialogCallback;
import com.incon.connect.custom.view.AppAlertDialog;
import com.incon.connect.custom.view.AppOtpDialog;
import com.incon.connect.custom.view.CustomAutoCompleteView;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addnewmodel.AddNewModelFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.warrantyregistration.adapter.ModelSearchArrayAdapter;
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
        WarrantRegistrationContract.View {

    private View rootView;
    private FragmentWarrantyRegistrationBinding binding;
    private WarrantRegistrationPresenter warrantRegistrationPresenter;
    private DisposableObserver<TextViewAfterTextChangeEvent> observer;
    private WarrantyRegistration warrantyRegistration;

    private ModelSearchArrayAdapter modelNumberAdapter;
    private List<ModelSearchResponse> warrantyregistrationList;
    private String selectedModelNumber;
    private int selectedPosition;
    boolean isOtpVerified;
    private AppAlertDialog warrantyStatusDialog;
    private AppOtpDialog dialog;
    private String enteredOtp;
    private boolean isSubmitClick = false;


    @Override
    protected void initializePresenter() {
        warrantRegistrationPresenter = new WarrantRegistrationPresenter();
        warrantRegistrationPresenter.setView(this);
        setBasePresenter(warrantRegistrationPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            binding.setFragment(this);
            warrantyRegistration = new WarrantyRegistration();
            binding.setWarrantyRegistration(warrantyRegistration);
            rootView = binding.getRoot();
            initializeModelNumberAdapter(new ArrayList<ModelSearchResponse>());
        }

        return rootView;
    }

    private void initializeModelNumberAdapter(List<ModelSearchResponse>
                                                      modelNumberList) {
        this.warrantyregistrationList = modelNumberList;
        modelNumberAdapter = new ModelSearchArrayAdapter(getContext(),
                modelNumberList);
        binding.edittextModelNumber.setAdapter(modelNumberAdapter);
        setObservableForModelNumber(binding.edittextModelNumber);

        binding.edittextModelNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                selectedPosition = pos;
                selectedModelNumber = warrantyregistrationList.get(
                        selectedPosition).getModelNumber();
            }
        });
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
    }

    @Override
    public void warrantyRegistered(Object warrantyRegisteredResponse) {
        showWarrantySuccessfulRegistionDialog();
    }

    @Override
    public void validateOtp() {
        isOtpVerified = true;
        if (isSubmitClick) {
            warrantRegistrationPresenter.doWarrantyRegistrationApi(warrantyRegistration);
        } else {
            ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                    AddNewModelFragment.class, null);
        }
    }


    public void onNewModelClick() {
        if (isOtpVerified) {
            ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                    AddNewModelFragment.class, null);
        } else {
            showOtpDialog();
        }
    }

    public void onSubmitClick() {
        if (isOtpVerified) {
            isSubmitClick = true;
            warrantRegistrationPresenter.doWarrantyRegistrationApi(warrantyRegistration);
        } else {
            showOtpDialog();
        }
    }

    private void showOtpDialog() {
        final String phoneNumber = "7799879990"; //todo remove later
        dialog = new AppOtpDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String otpString) {
                        enteredOtp = otpString;
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
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
                                warrantRegistrationPresenter.validateOTP(verifyOTP);
                                break;
                            case AlertDialogCallback.CANCEL:
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_verify_title, phoneNumber))
                .build();
        dialog.showDialog();
    }

    private void showWarrantySuccessfulRegistionDialog() {
        warrantyStatusDialog = new AppAlertDialog.AlertDialogBuilder(getActivity(), new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                            case AlertDialogCallback.CANCEL:
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
        if (observer != null) {
            observer.dispose();
        }
        warrantRegistrationPresenter.disposeAll();
    }
}
