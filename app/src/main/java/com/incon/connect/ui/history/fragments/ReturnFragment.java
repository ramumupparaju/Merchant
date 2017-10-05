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
import com.incon.connect.apimodel.components.history.purchased.ReturnResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.BottomSheetReturnBinding;
import com.incon.connect.databinding.CustomBottomViewBinding;
import com.incon.connect.databinding.FragmentReturnBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.ReturnAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PC on 10/2/2017.
 */

public class ReturnFragment extends BaseFragment implements ReturnContract.View {

    private FragmentReturnBinding binding;
    private View rootView;
    private List<ReturnResponse> returnList;
    private ReturnAdapter returnAdapter;

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetReturnBinding bottomSheetReturnBinding;

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return,
                    container, false);
            initViews();
            loadBottomSheet();

            rootView = binding.getRoot();
        }
        return rootView;
    }

    private void loadBottomSheet() {

        bottomSheetReturnBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getActivity()), R.layout.bottom_sheet_return, null, false);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetReturnBinding.getRoot());


    }

    private void initViews() {
        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        returnList = new ArrayList<>();
        returnAdapter = new ReturnAdapter();
        returnAdapter.setData(returnList);
        returnAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.returnRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.returnRecyclerview.setAdapter(returnAdapter);
        binding.returnRecyclerview.setLayoutManager(linearLayoutManager);
        addTestData();

    }

    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            ReturnResponse returnResponse = new ReturnResponse();
            returnResponse.setId(i);
            returnResponse.setPositionText();
            returnList.add(returnResponse);
        }
        returnAdapter.setData(returnList);
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
        bottomSheetReturnBinding.sheetTitle.setText("item : " + position);
        bottomSheetReturnBinding.topRow.setVisibility(View.GONE);
        bottomSheetReturnBinding.bottomRow.removeAllViews();

        int noOfViews = new Random().nextInt(4);
        for (int i = 0; i < noOfViews; i++) {
            CustomBottomViewBinding customBottomView = getCustomBottomView();
            customBottomView.viewTv.setText("position :" + i);
            View bottomRootView = customBottomView.getRoot();
            bottomRootView.setOnClickListener(bottomViewClickListener);
            bottomSheetReturnBinding.bottomRow.addView(bottomRootView);

        }
    }


    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetReturnBinding.topRow.removeAllViews();
            bottomSheetReturnBinding.topRow.setVisibility(View.VISIBLE);
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String bottomClickedText = viewById.getText().toString();
            int noOfViews = new Random().nextInt(4);
            for (int i = 0; i < noOfViews; i++) {
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomClickedText + i);
                View topRootView = customBottomView.getRoot();
                topRootView.setOnClickListener(topViewClickListener);
                bottomSheetReturnBinding.topRow.addView(topRootView);
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
                    returnAdapter.clearData();
                    // Get alerts of account api
                    addTestData();
                }
            };



}
