package com.incon.connect.ui.buyrequets;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.buyrequest.BuyRequestResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.FragmentBuyRequestBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.history.adapter.BuyRequestAdapter;
import com.incon.connect.ui.qrcodescan.QrcodeBarcodeScanActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class BuyRequestFragment extends BaseFragment implements BuyRequestContract.View {

        private static final String TAG = BuyRequestFragment.class.getSimpleName();
    private FragmentBuyRequestBinding binding;
    //    private ToolBarBinding toolBarBinding;
    private View rootView;
    private List<BuyRequestResponse> buyRequestList;
    private BuyRequestAdapter buyRequestAdapter;
    private String imageUrl = "https://thegreatergroup.com/wp-content/uploads/samsung-logo.jpeg";
    private String imageLogoUrl = "https://www.thegoodguys.com.au/wcsstore/TGGCAS/idcplg?"
           + "IdcService="
            + "GET_FILE&RevisionSelectionMethod=LatestReleased&noSaveAs=1&dDocName=50033329_119905"
           + "&Rendition=ZOOMIMAGE";

//    private Typeface defaultTypeFace;
//    private Typeface selectedTypeFace;
//    private String[] tabTitles;
//    private HistoryTabPagerAdapter adapter;

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_buy_request, container, false);
            rootView = binding.getRoot();
            initViews();
        }

        return rootView;
    }

    private void initViews() {

        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);

        buyRequestList = new ArrayList<>();
        buyRequestAdapter = new BuyRequestAdapter();
        buyRequestAdapter.setData(buyRequestList);
        buyRequestAdapter.setClickCallback(iClickCallback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.buyRequestRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.buyRequestRecyclerview.setAdapter(buyRequestAdapter);
        binding.buyRequestRecyclerview.setLayoutManager(linearLayoutManager);
    }
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    buyRequestAdapter.clearData();
                    // Get alerts of account api
                    addTestData();
                }
            };
    public void onScanningClick() {
        Intent intent = new Intent(getActivity(), QrcodeBarcodeScanActivity.class);
        startActivity(intent);
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            AppUtils.showSnackBar(getView() , "Accepted" + position);
        }
    };
    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            BuyRequestResponse taskResponse = new BuyRequestResponse();
            taskResponse.setId(i);
            taskResponse.setPositionText();
            taskResponse.setBrandName(imageUrl);
            taskResponse.setBrandType(imageLogoUrl);
            taskResponse.setBuyingStatus("Pending");
            taskResponse.setDateRequested("02/10/2017");
            buyRequestList.add(taskResponse);
        }
        buyRequestAdapter.setData(buyRequestList);
        dismissSwipeRefresh();
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }
}
