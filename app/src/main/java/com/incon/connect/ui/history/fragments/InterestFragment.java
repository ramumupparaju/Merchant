package com.incon.connect.ui.history.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.InterestResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.BottomSheetInterestBinding;
import com.incon.connect.databinding.CustomBottomViewBinding;
import com.incon.connect.databinding.FragmentInterestBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.InterestAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PC on 10/2/2017.
 */

public class InterestFragment extends BaseFragment implements InterestContract.View {
    FragmentInterestBinding binding;
    InterestPresenter interestPresenter;
    private List<InterestResponse> interestList;
    InterestAdapter interestAdapter;
    private BottomSheetInterestBinding bottomSheetInterestBinding;
    private BottomSheetDialog bottomSheetDialog;


    private View rootView;
    @Override
    protected void initializePresenter() {
        interestPresenter = new InterestPresenter();
        interestPresenter.setView(this);
        setBasePresenter(interestPresenter);

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_interest,
                    container, false);
            initViews();
            loadBottomSheet();


            rootView = binding.getRoot();
        }
        return rootView;
    }

    private void loadBottomSheet() {

         bottomSheetInterestBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getActivity()), R.layout.bottom_sheet_interest, null, false);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetInterestBinding.getRoot());


    }

    private void initViews() {
        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        interestList = new ArrayList<>();
        interestAdapter = new InterestAdapter();
        interestAdapter.setData(interestList);
        interestAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.interestRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.interestRecyclerview.setAdapter(interestAdapter);
        binding.interestRecyclerview.setLayoutManager(linearLayoutManager);
        addTestData();

    }

    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            InterestResponse interestResponse = new InterestResponse();
            interestResponse.setId(i);
            interestResponse.setPositionText();
            interestList.add(interestResponse);
        }
        interestAdapter.setData(interestList);
        dismissSwipeRefresh();


    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }


    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            createBottomSheetView(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetView(int position) {
        bottomSheetInterestBinding.sheetTitle.setText("item : " + position);
        bottomSheetInterestBinding.topRow.setVisibility(View.GONE);
        bottomSheetInterestBinding.bottomRow.removeAllViews();

        int noOfViews = new Random().nextInt(4);
        for (int i = 0; i < noOfViews; i++) {
            CustomBottomViewBinding customBottomView = getCustomBottomView();
            customBottomView.viewTv.setText("position :" + i);
            View bottomRootView = customBottomView.getRoot();
            bottomRootView.setOnClickListener(bottomViewClickListener);
            bottomSheetInterestBinding.bottomRow.addView(bottomRootView);

        }
    }


    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetInterestBinding.topRow.removeAllViews();
            bottomSheetInterestBinding.topRow.setVisibility(View.VISIBLE);
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String bottomClickedText = viewById.getText().toString();
            int noOfViews = new Random().nextInt(4);
            for (int i = 0; i < noOfViews; i++) {
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomClickedText + i);
                View topRootView = customBottomView.getRoot();
                topRootView.setOnClickListener(topViewClickListener);
                bottomSheetInterestBinding.topRow.addView(topRootView);
            }
        }
    };

    private View.OnClickListener topViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String topClickedText = viewById.getText().toString();
            showErrorMessage(topClickedText);
        }
    };

    private CustomBottomViewBinding getCustomBottomView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_bottom_view, null, false);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    interestAdapter.clearData();
                    // Get alerts of account api
                    addTestData();
                }
            };



}
