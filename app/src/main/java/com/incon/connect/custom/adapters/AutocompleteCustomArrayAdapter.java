package com.incon.connect.custom.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.databinding.ItemWarrantyregistrationFragmentBinding;

import java.util.List;

public class AutocompleteCustomArrayAdapter extends ArrayAdapter<WarrantyRegistrationResponse> {


    private Context mContext;
    private List<WarrantyRegistrationResponse> modelNumbersResponseList;
    private LayoutInflater layoutInflater;

    public AutocompleteCustomArrayAdapter(Context context, List<WarrantyRegistrationResponse>
            modelList) {
        super(context, R.layout.item_warrantyregistration_fragment, modelList);
        this.mContext = context;
        this.modelNumbersResponseList = modelList;
    }

    public void setData(List<WarrantyRegistrationResponse> modelNumbersResponseList) {
        this.modelNumbersResponseList = modelNumbersResponseList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(mContext);
        }

        // Perform the binding
        ItemWarrantyregistrationFragmentBinding binding = DataBindingUtil.getBinding(convertView);

        if (binding == null) {
            binding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_warrantyregistration_fragment, parent, false);
        }
        WarrantyRegistrationResponse modelNumber = modelNumbersResponseList.get(position);
        binding.setVariable(BR.warrantyregistrationResponse, modelNumber);
        binding.executePendingBindings();
        return binding.getRoot();
    }
}