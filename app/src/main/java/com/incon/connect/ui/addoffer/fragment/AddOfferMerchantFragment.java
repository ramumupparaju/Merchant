package com.incon.connect.ui.addoffer.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.addoffer.AddOfferMerchantFragmentResponse;
import com.incon.connect.databinding.FragmentAddOfferMerchantBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


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
            rootView = binding.getRoot();
            initViews();
        }

        return rootView;
    }

    private void initViews() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_offers));


    }

    @Override
    public void loadAddOfferMerchant(List<AddOfferMerchantFragmentResponse>
                                                 addOfferMerchantFragmentResponsesList) {


        if (addOfferMerchantFragmentResponsesList == null) {
            addOfferMerchantFragmentResponsesList = new ArrayList<>();
            this.addOfferMerchantList = addOfferMerchantFragmentResponsesList;
        }

    }
}
