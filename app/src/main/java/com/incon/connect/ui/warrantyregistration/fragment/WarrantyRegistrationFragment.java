package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addnewmodel.fragment.AddNewModelFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.warrantyregistration.fragment.adapter.WarrantyRegistrationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 9/28/2017.
 */

public class WarrantyRegistrationFragment extends BaseFragment {
    private FragmentWarrantyRegistrationBinding binding;
    private View rootView;
    private WarrantyRegistrationAdapter warrantyRegistrationAdapter;
    private List<WarrantyRegistrationResponse> warrantyregistrationList;
    String products[] = {"Samsung", "Redmi", "Moto"};
    @Override
    protected void initializePresenter() {

    }
    public void onFloatingClick() {
        ((HomeActivity) getActivity()).replaceFragment(
                AddNewModelFragment.class, null);

    }

    public void onSubmitClick() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            binding.setWarrantyregistration(this);
            rootView = binding.getRoot();

            initViews();
        }

        return rootView;
    }
    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            binding.searchView.setText(warrantyregistrationList.get(position).getName());
            binding.linearPriceDetails.setVisibility(View.VISIBLE);
            binding.recyclerviewData.setVisibility(View.GONE);

        }
    };

    private void initViews() {

        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);

        warrantyregistrationList = new ArrayList<>();
        for (int i = 0; i < products.length; i++) {
            warrantyregistrationList.add(new WarrantyRegistrationResponse(
                    products[i]

            ));
        }
        warrantyRegistrationAdapter = new WarrantyRegistrationAdapter();
        warrantyRegistrationAdapter.setData(warrantyregistrationList);
        warrantyRegistrationAdapter.setClickCallback(iClickCallback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.recyclerviewData.addItemDecoration(dividerItemDecoration);
        binding.recyclerviewData.setAdapter(warrantyRegistrationAdapter);
        binding.recyclerviewData.setLayoutManager(linearLayoutManager);


    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Get alerts of account api
                    addTestData();

                }
            };

    private void addTestData() {
       binding.searchView.setText(" ");
        binding.recyclerviewData.setVisibility(View.VISIBLE);
        binding.linearPriceDetails.setVisibility(View.GONE);
        dismissSwipeRefresh();

    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }

    }
}
