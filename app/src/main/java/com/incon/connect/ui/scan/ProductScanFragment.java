package com.incon.connect.ui.scan;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentProductScanBinding;
import com.incon.connect.dto.warrantyregistration.WarrantyRegistration;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistrationFragment;

/**
 * Created by PC on 10/6/2017.
 */
public class ProductScanFragment extends BaseFragment implements ProductScanContract.View {
    private View rootView;
    private FragmentProductScanBinding binding;
    private ProductScanPresenter productScanPresenter;

    @Override
    protected void initializePresenter() {
        productScanPresenter = new ProductScanPresenter();
        productScanPresenter.setView(this);
        setBasePresenter(productScanPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_product_scan, container, false);
            binding.setProductscanfragment(this);
            rootView = binding.getRoot();
        }
        return rootView;
    }

    public void onScanClick() {
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivityForResult(intent, RequestCodes.PRODUCT_WARRANTY_SCAN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.PRODUCT_WARRANTY_SCAN:
                    if (data != null) {
                        productScanPresenter.productInfoUsingQrCode(
                                data.getStringExtra(IntentConstants.SCANNED_CODE));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void onManualClick() {
        ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                WarrantyRegistrationFragment.class, getArguments());
    }

    @Override
    public void productInfo(Object productInfoResponse) {
        Bundle arguments = getArguments();
        WarrantyRegistration warrantyRegistration = arguments.getParcelable(
                BundleConstants.WARRANTY_DATA);
        //TODO have to assign values
        ((HomeActivity) getActivity()).replaceFragmentAndAddToStack(
                WarrantyRegistrationFragment.class, null);
    }
}
