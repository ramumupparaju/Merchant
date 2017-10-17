package com.incon.connect.ui.home.asignqrcode.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.search.ModelSearchResponse;
import com.incon.connect.custom.view.CustomAutoCompleteView;
import com.incon.connect.databinding.FragmentProductAssignBinding;
import com.incon.connect.dto.asignqrcode.AssignQrCode;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.warrantyregistration.adapter.ModelSearchArrayAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 10/6/2017.
 */
public class ProductAssignFragment extends BaseFragment implements ProductAssignContract.View {

    private View rootView;
    private FragmentProductAssignBinding binding;
    private ProductAssignPresenter assignPresenter;
    private AssignQrCode assignQrCode;
    private List<ModelSearchResponse> modelSearchResponseList;
    private ModelSearchArrayAdapter modelNumberAdapter;
    private int selectedPosition;
    private DisposableObserver<TextViewAfterTextChangeEvent> observer;
    private String selectedModelNumber;

    @Override
    protected void initializePresenter() {
        assignPresenter = new ProductAssignPresenter();
        assignPresenter.setView(this);
        setBasePresenter(assignPresenter);
    }

    public void onSubmitClick() {
        assignPresenter.assignQrCodeToProduct(assignQrCode);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_assign,
                    container, false);
            assignQrCode = new AssignQrCode();
            binding.setAssignQrCode(assignQrCode);
            binding.setProductassignfragment(this);

            rootView = binding.getRoot();
            initViews();
        }
        return rootView;
    }

    private void initViews() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.
                progress_qr_code_product));
        assignQrCode.setCode(getArguments().getString(BundleConstants.SCANNED_QRCODE));
        initializeModelNumberAdapter(new ArrayList<ModelSearchResponse>());
    }

    private void initializeModelNumberAdapter(List<ModelSearchResponse> modelNumberList) {
        this.modelSearchResponseList = modelNumberList;
        modelNumberAdapter = new ModelSearchArrayAdapter(getContext(),
                modelNumberList);
        binding.edittextModelNumber.setAdapter(modelNumberAdapter);
        setObservableForModelNumber(binding.edittextModelNumber);
        binding.edittextModelNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                selectedPosition = pos;
                ModelSearchResponse modelSearchResponse = modelSearchResponseList.get(pos);
                assignQrCode.setProductId(String.valueOf(modelSearchResponse.getId()));
                selectedModelNumber = modelSearchResponseList.get(
                        selectedPosition).getModelNumber();
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
                        assignPresenter.doModelSearchApi(modelNumberString);
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
        initializeModelNumberAdapter(modelSearchResponseList);
        binding.edittextModelNumber.showDropDown();
        if (modelSearchResponseList.size() == 0) {
            showErrorMessage(getString(R.string.error_message));
        }
    }

    @Override
    public void productAssignQrCode(Object assignQrCodeResponse) {
        Boolean assignQrcodeResult = (Boolean) assignQrCodeResponse;
        if (assignQrcodeResult) {
            AppUtils.shortToast(getContext(), "Assigned SuccessFully");
        }
    }
}
