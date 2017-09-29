package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    public void onSubmitClick() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            rootView = binding.getRoot();
        }
       /* adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_warranty,
                R.id.product_name, products);
        binding.listData.setAdapter(adapter);
        binding.listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // inputSearch.setText(String.valueOf(position));
                inputSearch.setText((String) parent.getItemAtPosition(position));
                lv.setVisibility(View.GONE);
                productDetailsLayout.setVisibility(View.VISIBLE);

            }
        });*/

        return rootView;
    }
}
