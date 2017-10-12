package com.incon.connect.ui.history.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.AppUtils;
import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.PurchasedHistoryResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemPurchasedFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13 Jun 2017 4:05 PM.
 *
 */
public class PurchasedAdapter extends RecyclerView.Adapter
        <PurchasedAdapter.ViewHolder>  {
    private List<PurchasedHistoryResponse> purchasedList = new ArrayList<>();
    private IClickCallback clickCallback;
    Context context;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPurchasedFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_purchased_fragment, parent, false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PurchasedHistoryResponse purchasedHistoryResponse = purchasedList.get(position);
        holder.bind(purchasedHistoryResponse);
    }

    @Override
    public int getItemCount() {
        return purchasedList.size();
    }


    public void setData(List<PurchasedHistoryResponse> purchasedHistoryResponseList) {
        purchasedList = purchasedHistoryResponseList;
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


        public void bind(PurchasedHistoryResponse purchasedHistoryResponse) {
            binding.setVariable(BR.purchasedHistoryResponse, purchasedHistoryResponse);
            AppUtils.loadImageFromApi(binding.brandImageview, purchasedHistoryResponse
                    .getProductLogoUrl());
            AppUtils.loadImageFromApi(binding.productImageImageview, purchasedHistoryResponse
                    .getProductImageUrl());
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }



   /* @BindingAdapter("android:src")
   public static void setImageResource(ImageView imageView, String resourceUrl) {
       Context context = imageView.getContext();
       Glide.with(context).
               load(resourceUrl).
              *//* error(R.drawable.arrow_forward).
               placeholder(R.drawable.arrow_forward).*//*
               into(imageView);
   }
*/

}
