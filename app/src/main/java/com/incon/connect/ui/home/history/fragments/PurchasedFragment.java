package com.incon.connect.ui.home.history.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.PurchasedResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.FragmentPurchasedBinding;
import com.incon.connect.databinding.ToolBarBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.home.history.adapter.PurchasedAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class PurchasedFragment extends BaseFragment implements PurchasedContract.View {

    private View rootView;
    private PurchasedPresenter purchasedPresenter;
    private FragmentPurchasedBinding binding;
    private ToolBarBinding toolBarBinding;
    private PurchasedAdapter purchasedAdapter;
    private List<PurchasedResponse> purchasedList;

    @Override
    protected void initializePresenter() {
        purchasedPresenter = new PurchasedPresenter();
        purchasedPresenter.setView(this);
        setBasePresenter(purchasedPresenter);
    }

    @Override
    protected void initializeToolBar() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        toolBarBinding = DataBindingUtil.inflate(layoutInflater, R.layout.tool_bar, null, false);
        ((HomeActivity) getActivity()).setSupportActionBar(toolBarBinding.toolbar);
        toolBarBinding.toolbarTitleTv.setText(R.string.title_history);
        toolBarBinding.toolbarLeftIv.setVisibility(View.GONE);
        toolBarBinding.toolbarRightIv.setVisibility(View.VISIBLE);
        toolBarBinding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ((HomeActivity) getActivity()).replaceToolBar(toolBarBinding.toolbar);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchased,
                    container, false);

            initViews();
            rootView = binding.getRoot();
        }
        return rootView;
    }

    private void initViews() {

        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);

        purchasedList = new ArrayList<>();
        purchasedAdapter = new PurchasedAdapter();
        purchasedAdapter.setData(purchasedList);
        purchasedAdapter.setClickCallback(iClickCallback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.purchasedRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.purchasedRecyclerview.setAdapter(purchasedAdapter);
        binding.purchasedRecyclerview.setLayoutManager(linearLayoutManager);

        addTestData();
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {

            showErrorMessage("Clicked :" + position);
        }
    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    purchasedAdapter.clearData();
                    // Get alerts of account api
                    addTestData();
                }
            };

    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            PurchasedResponse taskResponse = new PurchasedResponse();
            taskResponse.setId(i);
            taskResponse.setPositionText();
            purchasedList.add(taskResponse);
        }
        purchasedAdapter.setData(purchasedList);
        dismissSwipeRefresh();
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }
}
