package com.incon.connect.ui.addoffer.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemAddOfferMerchantFragmentBinding;
import com.incon.connect.dto.addoffer.AddOfferResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13 Jun 2017 4:05 PM.
 *
 */
public class AddOfferMerchantAdapter extends RecyclerView.Adapter
        <AddOfferMerchantAdapter.ViewHolder>  {
    private List<AddOfferResponse> buyRequestList = new ArrayList<>();
    private IClickCallback clickCallback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAddOfferMerchantFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_add_offer_merchant_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AddOfferResponse purchasedResponse = buyRequestList.get(position);
        holder.bind(purchasedResponse);
    }

    @Override
    public int getItemCount() {
        return buyRequestList.size();
    }


    public void setData(List<AddOfferResponse> taskResponseList) {
        buyRequestList = taskResponseList;
        notifyDataSetChanged();
    }

    public void clearData() {
        buyRequestList.clear();
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemAddOfferMerchantFragmentBinding binding;

        public ViewHolder(ItemAddOfferMerchantFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void bind(AddOfferResponse topCourse) {
            binding.setVariable(BR.addoffer, topCourse);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
