package com.incon.connect.ui.history.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.BR;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.InterestResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.ItemInterestFragmentBinding;
import com.incon.connect.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 10/2/2017.
 */

public class InterestAdapter extends RecyclerView.Adapter
        <InterestAdapter.ViewHolder> {
    private List<InterestResponse> lnterestList = new ArrayList<>();
    private IClickCallback clickCallback;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemInterestFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_interest_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InterestResponse interestResponse = lnterestList.get(position);
        holder.bind(interestResponse);



    }

    @Override
    public int getItemCount() {
        return lnterestList.size();
    }
    public  void setData(List<InterestResponse> taskResponseList) {
        lnterestList = taskResponseList;
        notifyDataSetChanged();

    }
    public void clearData() {
        lnterestList.clear();
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final ItemInterestFragmentBinding binding;
        public ViewHolder(ItemInterestFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }
        public void bind(InterestResponse topCourse) {
            binding.setVariable(BR.interestResponse, topCourse);
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
