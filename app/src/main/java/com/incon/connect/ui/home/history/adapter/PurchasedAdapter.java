package com.incon.connect.ui.home.history.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.PurchasedResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemPurchasedFragmentBinding;
import com.incon.connect.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13 Jun 2017 4:05 PM.
 *
 */
public class PurchasedAdapter extends RecyclerView.Adapter
        <PurchasedAdapter.ViewHolder>  {
    private List<PurchasedResponse> purchasedList = new ArrayList<>();
    private IClickCallback clickCallback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPurchasedFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_purchased_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PurchasedResponse purchasedResponse = purchasedList.get(position);
        holder.bind(purchasedResponse);
    }

    @Override
    public int getItemCount() {
        return purchasedList.size();
    }


    public void setData(List<PurchasedResponse> taskResponseList) {
        purchasedList = taskResponseList;
        notifyDataSetChanged();
    }

    public void clearData() {
        purchasedList.clear();
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemPurchasedFragmentBinding binding;

        public ViewHolder(ItemPurchasedFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }


        public void bind(PurchasedResponse topCourse) {
            binding.setVariable(BR.purchasedResponse, topCourse);
            binding.textTaskTime.setText(DateUtils.formatTimeDay(System.currentTimeMillis()
                    - topCourse.getId() * 1000));
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
