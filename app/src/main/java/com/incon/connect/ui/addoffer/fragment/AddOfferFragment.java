package com.incon.connect.ui.addoffer.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.incon.connect.R;
import com.incon.connect.custom.view.AddOfferViewHolder;
import com.incon.connect.databinding.FragmentAddofferBinding;
import com.incon.connect.dto.addoffer.AddOffer;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addoffer.adapter.AddOfferAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by PC on 9/28/2017.
 */

public class AddOfferFragment extends BaseFragment {
    FragmentAddofferBinding binding;
    ListView listaddoffer;
    private ArrayAdapter<AddOffer> addofferAdapter;
    private AddOffer[] addOffers;
    private View rootView;
    @Override
    protected void initializePresenter() {

    }
    public void onSelectedClick() {

        for (int i = 0; i < addofferAdapter.getCount(); i++) {
            AddOffer addOffer = addofferAdapter.getItem(i);
            if (addOffer.isChecked()) {
                Toast.makeText(getActivity(),
                        addOffer.getName() + " is Checked!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.activity_addoffer,
                    container, false);

            rootView = binding.getRoot();
        }

        listaddoffer = (ListView) rootView.findViewById(R.id.list_addoffer);
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
     //  addOffers = (AddOffer[]) getLastNonConfigurationInstance();
        if (addOffers == null) {
            addOffers = new AddOffer[] {
                    new AddOffer("Brand"), new AddOffer("Division"), new AddOffer("Model"),
                    new AddOffer("Date"), new AddOffer("Add an Offer on MRP"),
                    new AddOffer("Offer ecpires on")};
        }
        ArrayList<AddOffer> addOfferArrayList = new ArrayList<AddOffer>();
        addOfferArrayList.addAll(Arrays.asList(addOffers));

        addofferAdapter = new AddOfferAdapter(getActivity(), addOfferArrayList);
        listaddoffer.setAdapter(addofferAdapter);

        return rootView;
    }
}
