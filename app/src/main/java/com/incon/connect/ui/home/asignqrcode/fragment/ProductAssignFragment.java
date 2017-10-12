package com.incon.connect.ui.home.asignqrcode.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.databinding.FragmentProductAssignBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
/**
 * Created by PC on 10/6/2017.
 */
public class ProductAssignFragment extends BaseFragment implements ProductAssignContract.View {

    private View rootView;
    private FragmentProductAssignBinding binding;
    private ProductAssignPresenter assignPresenter;

    @Override
    protected void initializePresenter() {
        assignPresenter = new ProductAssignPresenter();
        assignPresenter.setView(this);
        setBasePresenter(assignPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_assign,
                    container, false);
            initViews();

            rootView = binding.getRoot();
        }
        return rootView;
    }

    private void initViews() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.
                progress_qr_code_product));
    }

    @Override
    public void productAssignQrCode(Object assignQrCodeResponse) {
        if (assignQrCodeResponse.equals("true")) {
            AppUtils.shortToast(getContext(), "Succes");
        }
    }
}
