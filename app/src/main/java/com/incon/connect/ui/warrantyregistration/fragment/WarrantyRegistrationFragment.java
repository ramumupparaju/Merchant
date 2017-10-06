package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.warrantyegistration.WarrantyRegistrationResponse;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addnewmodel.fragment.AddNewModelFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.warrantyregistration.WarrantRegistrationContract;
import com.incon.connect.ui.warrantyregistration.WarrantRegistrationPresenter;
import com.incon.connect.ui.warrantyregistration.fragment.adapter.WarrantyRegistrationAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 9/28/2017.
 */
public class WarrantyRegistrationFragment extends BaseFragment implements
        WarrantRegistrationContract.View {

    private View rootView;
    private FragmentWarrantyRegistrationBinding binding;
    private WarrantRegistrationPresenter warrantRegistrationPresenter;

    private WarrantyRegistrationAdapter warrantyRegistrationAdapter;
    private List<WarrantyRegistrationResponse> warrantyregistrationList;
    private String selectedModelNumber;

    @Override
    protected void initializePresenter() {

        warrantRegistrationPresenter = new WarrantRegistrationPresenter();
        warrantRegistrationPresenter.setView(this);
        setBasePresenter(warrantRegistrationPresenter);
    }

    public void onFloatingClick() {
        ((HomeActivity) getActivity()).replaceFragment(
                AddNewModelFragment.class, null);

    }

    public void onSubmitClick() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_warranty_registration, container, false);
            binding.setWarrantyregistration(this);
            rootView = binding.getRoot();

            initViews();
        }

        return rootView;
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            binding.modelNumberRecyclerview.setVisibility(View.GONE);
            setTextForSearchView((warrantyregistrationList.get(position).getName()));
            binding.linearPriceDetails.setVisibility(View.VISIBLE);
            binding.modelNumberRecyclerview.setVisibility(View.GONE);

        }
    };

    private void setTextForSearchView(String textToSet) {
        selectedModelNumber = textToSet;
        binding.edittextModelNumber.setText(textToSet);
        binding.edittextModelNumber.clearFocus();
    }

    private void initViews() {


        warrantyregistrationList = new ArrayList<>();
        warrantyRegistrationAdapter = new WarrantyRegistrationAdapter();
        warrantyRegistrationAdapter.setData(warrantyregistrationList);
        warrantyRegistrationAdapter.setClickCallback(iClickCallback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.modelNumberRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.modelNumberRecyclerview.setAdapter(warrantyRegistrationAdapter);
        binding.modelNumberRecyclerview.setLayoutManager(linearLayoutManager);
        binding.modelNumberRecyclerview.setVisibility(View.GONE);

        setObservadleForModelNumber(binding.edittextModelNumber);
    }

    private void setObservadleForModelNumber(TextInputEditText edittextModelNumber) {
        DisposableObserver<TextViewAfterTextChangeEvent> observer =
                new DisposableObserver<TextViewAfterTextChangeEvent>() {

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String modelNumberString = textViewAfterTextChangeEvent.editable()
                                .toString();
                        if ((TextUtils.isEmpty(selectedModelNumber) || !selectedModelNumber.equals(
                                modelNumberString))) {
                            if (modelNumberString.length() > WarrantyRegistrationConstants
                                    .MINIMUM_MODELNUMBER_TO_SEARCH) {
                                warrantRegistrationPresenter.doModelSearchApi(modelNumberString);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        RxTextView.afterTextChangeEvents(edittextModelNumber)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void loadModelNumberData() {
        binding.modelNumberRecyclerview.setVisibility(View.VISIBLE);
        warrantyregistrationList.clear();

        int sampleSearches = new Random().nextInt(4) + 1;
        for (int i = 0; i < sampleSearches; i++) {
            warrantyregistrationList.add(new WarrantyRegistrationResponse("pos : " + i));
        }
        warrantyRegistrationAdapter.setData(warrantyregistrationList);
        binding.modelNumberRecyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        warrantRegistrationPresenter.disposeAll();
    }
}
