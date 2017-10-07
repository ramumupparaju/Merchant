package com.incon.connect.ui.home.asignqrcode.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.databinding.FragmentProductAssignBinding;
import com.incon.connect.ui.BaseFragment;

/**
 * Created by PC on 10/6/2017.
 */

public class ProductAssignFragment extends BaseFragment {

    private View rootView;
    FragmentProductAssignBinding binding;


    @Override
    protected void initializePresenter() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_assign,
                    container, false);


            rootView = binding.getRoot();
        }
        return rootView;
    }
}
