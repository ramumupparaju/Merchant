package com.incon.connect.ui.warrantyregistration.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.incon.connect.R;
import com.incon.connect.custom.view.CustomAutoCompleteView;
import com.incon.connect.databinding.FragmentWarrantyRegistrationBinding;
import com.incon.connect.dto.model.search.ModelSearchResponse;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.addnewmodel.fragment.AddNewModelFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.warrantyregistration.WarrantRegistrationContract;
import com.incon.connect.ui.warrantyregistration.WarrantRegistrationPresenter;
import com.incon.connect.ui.warrantyregistration.fragment.adapter.ModelSearchArrayAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
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
    private DisposableObserver<TextViewAfterTextChangeEvent> observer;

    private ModelSearchArrayAdapter modelNumberAdapter;
    private List<ModelSearchResponse> warrantyregistrationList;
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

            initializeModelNumberAdapter(new ArrayList<ModelSearchResponse>());
        }

        return rootView;
    }

    private void initializeModelNumberAdapter(List<ModelSearchResponse>
                                                   modelNumberList) {
        this.warrantyregistrationList = modelNumberList;
        modelNumberAdapter = new ModelSearchArrayAdapter(getContext(),
                modelNumberList);
        binding.edittextModelNumber.setAdapter(modelNumberAdapter);
        setObservableForModelNumber(binding.edittextModelNumber);

        binding.edittextModelNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
//                selectedModelNumber = warrantyregistrationList.get(pos).getName();
            }
        });
    }

    private void setObservableForModelNumber(CustomAutoCompleteView edittextModelNumber) {
        if (observer != null) {
            observer.dispose();
        }
        observer = new DisposableObserver<TextViewAfterTextChangeEvent>() {

            @Override
            public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                String modelNumberString = textViewAfterTextChangeEvent.editable()
                        .toString();
                if ((TextUtils.isEmpty(selectedModelNumber) || !selectedModelNumber.equals(
                        modelNumberString))) {
                    if (modelNumberString.length() > WarrantyRegistrationConstants
                            .MINIMUM_MODELNUMBER_TO_SEARCH) {
                        warrantRegistrationPresenter.doModelSearchApi(modelNumberString);
                        selectedModelNumber = modelNumberString;
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
    public void loadModelNumberData(List<ModelSearchResponse> modelSearchResponseList) {

        if (modelSearchResponseList == null) {
            modelSearchResponseList = new ArrayList<>();
        }
        initializeModelNumberAdapter(warrantyregistrationList);
        binding.edittextModelNumber.showDropDown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (observer != null) {
            observer.dispose();
        }
        warrantRegistrationPresenter.disposeAll();
    }
}
