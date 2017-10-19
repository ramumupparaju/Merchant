package com.incon.connect.ui.scan;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.databinding.FragmentScanTabBinding;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.utils.ValidationUtils;


public class ScanTabFragment extends BaseFragment implements ScanTabContract.View {

    private static final String TAG = ScanTabFragment.class.getSimpleName();
    private View rootView;
    private static final int PHONE_NUMBER_EDIT_UI = 1;
    private static final int SCAN_OPTIONS_UI = 2;
    private FragmentScanTabBinding binding;
    private ScanTabPresenter scanTabPresenter;


    @Override
    protected void initializePresenter() {
        scanTabPresenter = new ScanTabPresenter();
        scanTabPresenter.setView(this);
        setBasePresenter(scanTabPresenter);
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
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_scan));
    }

    public void onScanClick() {
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivityForResult(intent, RequestCodes.USER_PROFILE_SCAN);
    }

    public void onPhoneNumberClick() {
        showUIType(PHONE_NUMBER_EDIT_UI);
    }

    public void onDoneClick() {
        String phoneNumber = binding.phoneNumberEt.getText().toString();
        if (ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            binding.textMobilenumber.setText(phoneNumber);
            showUIType(SCAN_OPTIONS_UI);
            scanTabPresenter.userInfoUsingPhoneNumber(phoneNumber);
        } else {
            showErrorMessage(getString(R.string.error_phone_min_digits));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.USER_PROFILE_SCAN:
                    if (data != null) {
                        scanTabPresenter.userInfoUsingQrCode(
                                data.getStringExtra(IntentConstants.SCANNED_CODE));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void userInfo(UserInfoResponse userInfoResponse) {
        WarrantyRegistration warrantyRegistration = new WarrantyRegistration();
        warrantyRegistration.setMobileNumber(userInfoResponse.getMsisdn());
        warrantyRegistration.setCustomerId(String.valueOf(userInfoResponse.getId()));
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleConstants.WARRANTY_DATA, warrantyRegistration);
        ((HomeActivity) getActivity()).replaceFragment(
                ProductScanFragment.class, bundle);
    }
}