package com.incon.connect.ui.warrantyregistration;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseActivity;

/**
 * Created by PC on 9/25/2017.
 */

public class WarrantyRegistration extends BaseActivity {
    private ListView lv;
    PopupWindow pw;
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
    public void onFloatingClick() {

       // showPopUp();

    }

    private void showPopUp() {
        LayoutInflater inflater = (LayoutInflater) WarrantyRegistration.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_floatingbutton,
                (ViewGroup) findViewById(R.id.popup_float));
        pw = new PopupWindow(layout, 200, 200, true);
        // display the popup in the center
        pw.showAtLocation(layout, Gravity.RIGHT, 0, 0);


    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        activityWarrantyRegistrationBinding = DataBindingUtil.setContentView(this, getLayoutId());
        activityWarrantyRegistrationBinding.setWarrantyregistration(this);

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
               // inputSearch.setText(String.valueOf(position));
                inputSearch.setText((String) parent.getItemAtPosition(position));
                lv.setVisibility(View.GONE);
                productDetailsLayout.setVisibility(View.VISIBLE);

            }
        });

    }
}
