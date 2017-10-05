package com.incon.connect.ui.warrantyregistration.fragment.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemWarrantyregistrationFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 10/5/2017.
 */

public class WarrantyRegistrationAdapter extends RecyclerView.Adapter
        <WarrantyRegistrationAdapter.ViewHolder> {
    private IClickCallback clickCallback;
    private List<WarrantyRegistrationResponse> warrantyregistrationList = new ArrayList<>();
    @Override
    public WarrantyRegistrationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemWarrantyregistrationFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_warrantyregistration_fragment, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(WarrantyRegistrationAdapter.ViewHolder holder, int position) {
        WarrantyRegistrationResponse warrantyRegistrationResponse =
                warrantyregistrationList.get(position);
        holder.bind(warrantyRegistrationResponse);

    }
    public void setData(List<WarrantyRegistrationResponse> taskResponseList) {
        warrantyregistrationList = taskResponseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return warrantyregistrationList.size();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         private ItemWarrantyregistrationFragmentBinding binding;
        public ViewHolder(ItemWarrantyregistrationFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(WarrantyRegistrationResponse topCourse) {


            binding.setVariable(BR.warrantyregistrationResponse, topCourse);
            binding.executePendingBindings();

        }


        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());

        }
    }
}
