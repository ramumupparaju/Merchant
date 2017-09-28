package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseFragment;

/**
 * Created by PC on 9/28/2017.
 */

public class WarrantyRegistrationFragment extends BaseFragment {
    FragmentWarrantyRegistrationBinding binding;
    EditText inputSearch;
    LinearLayout productDetailsLayout;
    ArrayAdapter<String> adapter;
    private ListView lv;
    private View rootView;
    String products[] = {"Samsung", "Redmi", "Moto"};
    @Override
    protected void initializePresenter() {

    }
    public void onFloatingClick() {
        Toast.makeText(getActivity(), "Floating button click", Toast.LENGTH_LONG).show();


    }

    public void onSubmitClick() {
        Toast.makeText(getActivity(), "submit button clicked", Toast.LENGTH_LONG).show();


    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            rootView = binding.getRoot();
        }
        lv = (ListView) getActivity().findViewById(R.id.list_data);
        inputSearch = (EditText)  getActivity().findViewById(R.id.search_view);
        productDetailsLayout = (LinearLayout) getActivity().findViewById(R.id.linear_price_details);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_text,
                R.id.product_name, products);
        lv.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                WarrantyRegistrationFragment.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // inputSearch.setText(String.valueOf(position));
                inputSearch.setText((String) parent.getItemAtPosition(position));
                lv.setVisibility(View.GONE);
                productDetailsLayout.setVisibility(View.VISIBLE);

            }
        });
        return rootView;
    }
}
