package com.incon.connect.ui.history.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.history.purchased.PurchasedHistoryResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.BottomSheetPurchasedBinding;
import com.incon.connect.databinding.CustomBottomViewBinding;
import com.incon.connect.databinding.FragmentPurchasedBinding;
import com.incon.connect.ui.RegistrationMapActivity;
import com.incon.connect.ui.history.adapter.PurchasedAdapter;
import com.incon.connect.ui.history.base.BaseTabFragment;
import com.incon.connect.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class PurchasedFragment extends BaseTabFragment implements PurchasedContract.View {

    private View rootView;
    private PurchasedPresenter purchasedPresenter;
    private FragmentPurchasedBinding binding;
    private PurchasedAdapter purchasedAdapter;
    private List<PurchasedHistoryResponse> purchasedList;
    private int userId;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetPurchasedBinding bottomSheetPurchasedBinding;
    private int position1;

    @Override
    protected void initializePresenter() {
        purchasedPresenter = new PurchasedPresenter();
        purchasedPresenter.setView(this);
        setBasePresenter(purchasedPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchased,
                    container, false);

            loadBottomSheet();
            initViews();
            rootView = binding.getRoot();
        }
        return rootView;
    }

    private void loadBottomSheet() {
        bottomSheetPurchasedBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getActivity()), R.layout.bottom_sheet_purchased, null, false);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetPurchasedBinding.getRoot());
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                purchasedAdapter.clearSelection();
            }
        });
        /*dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);*/
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


        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
        purchasedPresenter.purchased(userId);
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            //Todo have to get item from filter list
            purchasedAdapter.clearSelection();
            PurchasedHistoryResponse purchasedHistoryResponse = purchasedList.get(position);
            purchasedHistoryResponse.setSelected(true);
            purchasedAdapter.notifyDataSetChanged();
            createBottomSheetView(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetView(int position) {
        position1 = position;
        bottomSheetPurchasedBinding.topRow.setVisibility(View.GONE);
        // bottomSheetPurchasedBinding.bottomRow.removeAllViews();

        String[] bottomNames = new String[4];
        bottomNames[0] = "Customer";
        bottomNames[1] = "Product";
        bottomNames[2] = "Service/Support";
        bottomNames[3] = "Satus Update";


        bottomSheetPurchasedBinding.bottomRow.removeAllViews();
        int length = bottomNames.length;
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, length);
        params.setMargins(1, 1, 1, 1);
//TODO have to remove hard codeings
        for (int i = 0; i < length; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setWeightSum(1f);
            linearLayout.setGravity(Gravity.CENTER);
            CustomBottomViewBinding customBottomView = getCustomBottomView();
            customBottomView.viewTv.setText(bottomNames[i]);
            View bottomRootView = customBottomView.getRoot();
            bottomRootView.setTag(i);
            linearLayout.addView(bottomRootView);
            bottomRootView.setOnClickListener(bottomViewClickListener);
            bottomSheetPurchasedBinding.bottomRow.addView(linearLayout, params);
        }
    }

    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            String[] bottomOptions;
            if (tag == 0) {
                bottomOptions = new String[2];
                bottomOptions[0] = "Call";
                bottomOptions[1] = "Location";
            } else if (tag == 1) {
                bottomOptions = new String[2];
                bottomOptions[0] = "Details";
                bottomOptions[1] = "Warranty";
            } else if (tag == 2) {
                bottomOptions = new String[3];
                bottomOptions[0] = "Call";
                bottomOptions[1] = "Request Installation";
                bottomOptions[2] = "Share Customer Location";
            } else {
                bottomOptions = new String[4];
                bottomOptions[0] = "Dispatches On";
                bottomOptions[1] = "Dispatched";
                bottomOptions[2] = "Delivered";
                bottomOptions[3] = "Installed";
            }
            bottomSheetPurchasedBinding.topRow.removeAllViews();
            int length1 = bottomOptions.length;
            bottomSheetPurchasedBinding.topRow.setVisibility(View.VISIBLE);
            int length = length1;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT, length);
            params.setMargins(1, 1, 1, 1);
            for (int i = 0; i < length; i++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setWeightSum(1f);
                linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomOptions[i]);
                View topRootView = customBottomView.getRoot();
                topRootView.setTag(i);
                topRootView.setOnClickListener(topViewClickListener);
                linearLayout.addView(topRootView);
                bottomSheetPurchasedBinding.topRow.addView(linearLayout, params);
            }
        }
    };

    private View.OnClickListener topViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String topClickedText = viewById.getText().toString();
            showErrorMessage(topClickedText);
            Integer tag = (Integer) view.getTag();
            if (tag == 0 && topClickedText.equals("Call")) {
                callPhoneNumber(purchasedList.get(position1).getMobileNumber());
            } else if (tag == 1 && topClickedText.equals("Location")) {
                onOpenLocation();
            } else if (tag == 0 && topClickedText.equals("Details")) {
                onOpenAlert();
            }
        }
    };

    private void onOpenAlert() {
    }

    private void onOpenLocation() {
            Intent addressIntent = new Intent(getActivity(),
                    RegistrationMapActivity.class);
            getActivity().startActivity(addressIntent);
    }

    private void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(intent);
    }

    private CustomBottomViewBinding getCustomBottomView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_bottom_view, null, false);
    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    purchasedAdapter.clearData();
                    purchasedPresenter.purchased(userId);
                }
            };


    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadPurchasedHistory(List<PurchasedHistoryResponse> purchasedHistoryResponseList) {
        if (purchasedHistoryResponseList == null) {
            purchasedHistoryResponseList = new ArrayList<>();
        }
        this.purchasedList = purchasedHistoryResponseList;
        purchasedAdapter.setData(purchasedList);
        dismissSwipeRefresh();
    }

    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        AppUtils.hideSoftKeyboard(getActivity(), rootView);
        purchasedAdapter.searchData(searchableText, searchType);
    }
}
