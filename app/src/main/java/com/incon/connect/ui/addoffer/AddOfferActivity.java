package com.incon.connect.ui.addoffer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.incon.connect.R;
import com.incon.connect.custom.view.AddOfferViewHolder;
import com.incon.connect.databinding.ActivityAddofferBinding;
import com.incon.connect.dto.addoffer.AddOffer;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.addoffer.adapter.AddOfferAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by PC on 9/27/2017.
 */

public class AddOfferActivity extends BaseActivity {
    ActivityAddofferBinding addofferBinding;
    ListView listaddoffer;
    private ArrayAdapter<AddOffer> addofferAdapter;
    private AddOffer[] addOffers;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_addoffer;
    }

    @Override
    protected void initializePresenter() {

    }
    public void onSelectedClick() {

        for (int i = 0; i < addofferAdapter.getCount(); i++) {
            AddOffer addOffer = addofferAdapter.getItem(i);
            if (addOffer.isChecked()) {
                Toast.makeText(getApplicationContext(),
                        addOffer.getName() + " is Checked!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {

        addofferBinding = DataBindingUtil.setContentView(this, getLayoutId());
        addofferBinding.setAddoffer(this);
        listaddoffer = (ListView) findViewById(R.id.list_addoffer);
        listaddoffer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AddOffer planet = addofferAdapter.getItem(position);
                planet.toggleChecked();
                AddOfferViewHolder viewHolder = (AddOfferViewHolder) view
                        .getTag();
                viewHolder.getCheckBox().setChecked(planet.isChecked());
            }
        });
        addOffers = (AddOffer[]) getLastNonConfigurationInstance();
        if (addOffers == null) {
            addOffers = new AddOffer[] {
                    new AddOffer("Brand"), new AddOffer("Division"), new AddOffer("Model"),
                            new AddOffer("Date"), new AddOffer("Add an Offer on MRP: %"),
                            new AddOffer("Offer expires on")};
        }
        ArrayList<AddOffer> addOfferArrayList = new ArrayList<AddOffer>();
        addOfferArrayList.addAll(Arrays.asList(addOffers));

        addofferAdapter = new AddOfferAdapter(this, addOfferArrayList);
        listaddoffer.setAdapter(addofferAdapter);




    }
}
