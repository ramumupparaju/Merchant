package com.incon.connect.ui.scan;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    ImageView imageView;
    TextView textView;

    @Override
    protected void initializePresenter() {
    }
    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_scan_tab, container, false);
            rootView = binding.getRoot();
            imageView = (ImageView) rootView.findViewById(R.id.im_scann);
            textView = (TextView) rootView.findViewById(R.id.text_mobilenumber);
            initViews();
        }
        System.out.println("Click oncreate called ");

        return rootView;
    }
    public void initViews(){

    }



    public void onScanningClick() {
        System.out.println("Click Event method should be called ");
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivity(intent);

    }

    public void onTextClick() {
        System.out.println("Click Edit Event method should be called ");
        ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                WarrantyRegistrationFragment.class, null);
       /* Intent intent = new Intent(getActivity(), WarrantyRegistrationActivity.class);
        startActivity(intent);*/
    }
}