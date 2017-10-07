package com.incon.connect.ui.addnewmodel.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentAddnewmodelBinding;
import com.incon.connect.ui.BaseFragment;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelFragment extends BaseFragment {
    private FragmentAddnewmodelBinding binding;
    private View rootView;
    private ArrayAdapter<String> adapter;
    String products[] = {"BPL", "ONIDA", "LG"};
    @Override
    protected void initializePresenter() {

    }
    public void onSubmitClick() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_addnewmodel, container, false);
            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    private void initViews() {

        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);

    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    dismissSwipeRefresh();

                }
            };

    private void dismissSwipeRefresh() {

        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }


    }
}
