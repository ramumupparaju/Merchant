package com.incon.connect.ui.scan;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentScanTabBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.fragments.PurchasedContract;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.utils.ValidationUtils;


public class ScanTabFragment extends BaseFragment implements PurchasedContract.View {

    private static final String TAG = ScanTabFragment.class.getSimpleName();
    private static final int PHONE_NUMBER_EDIT_UI = 1;
    private static final int SCAN_OPTIONS_UI = 2;
    private FragmentScanTabBinding binding;

    private View rootView;

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_scan_tab, container, false);
            binding.setScanning(this);
            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    public void initViews() {

    }

    public void onScanClick() {
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivityForResult(intent, RequestCodes.USER_PROFILE_SCAN);
    }

    public void onPhoneNumberClick() {
        showUIType(PHONE_NUMBER_EDIT_UI);
    }

    public void onDoneClick() {
        //TODO check phone number is valid or not if valid call api cal
        String phoneNumber = binding.phoneNumberEt.getText().toString();
        if (ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            //TODO API CALL

            binding.textMobilenumber.setText(phoneNumber);
            showUIType(SCAN_OPTIONS_UI);
        }
    }

    public void onCancelClick() {
        showUIType(SCAN_OPTIONS_UI);
    }

    private void showUIType(int uiType) {
        if (uiType == PHONE_NUMBER_EDIT_UI) {
            binding.scanUi.setVisibility(View.GONE);
            binding.phoneNumberEditLayout.setVisibility(View.VISIBLE);
        } else if (uiType == SCAN_OPTIONS_UI) {
            binding.scanUi.setVisibility(View.VISIBLE);
            binding.phoneNumberEditLayout.setVisibility(View.GONE);
        }
    }

    private void onWarrentyStarts() {
        ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                ProductScanFragment.class, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.USER_PROFILE_SCAN:
                    callUserProfileApiUsingQRCode();
                    break;
                default:
                    break;
            }
        }
    }

    private void callUserProfileApiUsingQRCode() {
        //TODO api call
        onWarrentyStarts();
    }
}