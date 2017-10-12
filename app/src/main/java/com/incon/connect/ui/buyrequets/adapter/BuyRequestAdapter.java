package com.incon.connect.ui.buyrequets.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.buyrequest.BuyRequestResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemBuyRequestFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13 Jun 2017 4:05 PM.
 *
 */
public class BuyRequestAdapter extends RecyclerView.Adapter
        <BuyRequestAdapter.ViewHolder>  {
    private List<BuyRequestResponse> buyRequestList = new ArrayList<>();
    private IClickCallback clickCallback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBuyRequestFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_buy_request_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuyRequestResponse purchasedResponse = buyRequestList.get(position);


        holder.bind(purchasedResponse);
    }

    @Override
    public int getItemCount() {
        return buyRequestList.size();
    }


    public void setData(List<BuyRequestResponse> buyRequestResponseList) {
        buyRequestList = buyRequestResponseList;
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
        private final ItemBuyRequestFragmentBinding binding;

        public ViewHolder(ItemBuyRequestFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void bind(BuyRequestResponse buyRequestResponse) {
            binding.setVariable(BR.buyRequestResponse, buyRequestResponse);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
