package com.incon.connect.ui.addnewmodel.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentAddNewModelBinding;
import com.incon.connect.ui.BaseFragment;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelFragment extends BaseFragment {
    private FragmentAddNewModelBinding binding;
    private View rootView;
    @Override
    protected void initializePresenter() {

    }


    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_add_new_model, container, false);
            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    private void initViews() {


    }



}
