package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseFragment;

/**
 * Created by PC on 9/28/2017.
 */

public class WarrantyRegistrationFragment extends BaseFragment {
    private FragmentWarrantyRegistrationBinding binding;
    private View rootView;

    private ArrayAdapter<String> adapter;
    String products[] = {"Samsung", "Redmi", "Moto"};
    @Override
    protected void initializePresenter() {

    }
    public void onFloatingClick() {
        binding.searchView.setText(" ");
        binding.listData.setVisibility(View.VISIBLE);
        binding.linearPriceDetails.setVisibility(View.GONE);
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
        }
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_warranty,
                R.id.product_name, products);
        binding.listData.setAdapter(adapter);
        binding.listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.searchView.setText((String) parent.getItemAtPosition(position));
                binding.listData.setVisibility(View.GONE);
                binding.linearPriceDetails.setVisibility(View.VISIBLE);

            }
        });

        return rootView;
    }
}
