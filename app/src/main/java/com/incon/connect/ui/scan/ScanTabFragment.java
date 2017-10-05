package com.incon.connect.ui.scan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentScanTabBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.ui.warrantyregistration.fragment.WarrantyRegistrationFragment;


public class ScanTabFragment extends BaseFragment {

    private static final String TAG = ScanTabFragment.class.getSimpleName();
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
            binding.setScantabfragment(this);
            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    public void initViews() {

    }

    public void onScanClick() {
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivity(intent);
    }

    public void onManualClick() {
        ((HomeActivity) getActivity()).replaceFragment(
                WarrantyRegistrationFragment.class, null);
       /* Intent intent = new Intent(getActivity(), WarrantyRegistrationActivity.class);
        startActivity(intent);*/
    }
}