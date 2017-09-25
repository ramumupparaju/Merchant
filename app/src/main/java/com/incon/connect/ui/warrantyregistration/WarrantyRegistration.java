package com.incon.connect.ui.warrantyregistration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseActivity;

/**
 * Created by PC on 9/25/2017.
 */

public class WarrantyRegistration extends BaseActivity {
    private ListView lv;
    ActivityWarrantyRegistrationBinding activityWarrantyRegistrationBinding;
    EditText inputSearch;
    LinearLayout productDetailsLayout;

    ArrayAdapter<String> adapter;
    String products[] = {"Samsung", "Redmi", "Moto"};
    @Override
    protected int getLayoutId() {
        return  R.layout.activity_warranty_registration;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityWarrantyRegistrationBinding = DataBindingUtil.setContentView(this, getLayoutId());


        lv = (ListView) findViewById(R.id.list_data);
        inputSearch = (EditText) findViewById(R.id.search_view);
        productDetailsLayout = (LinearLayout) findViewById(R.id.linear_price_details);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_text,
                R.id.product_name, products);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                WarrantyRegistration.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputSearch.setText(String.valueOf(position));
                lv.setVisibility(View.GONE);
                productDetailsLayout.setVisibility(View.VISIBLE);

            }
        });

    }
}
