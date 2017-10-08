package com.incon.connect.ui.addnewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.dto.addnewmodel.AddNewModel;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 10/4/2017.
 */

public class AddNewModelPresenter extends BasePresenter<AddNewModelContract.View> implements
        AddNewModelContract.Presenter {

    private static final String TAG = AddNewModelPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void addingNewModel(int merchantId, AddNewModel addNewModel) {
        getView().showProgress(appContext.getString(R.string.progress_add_new_model));
        DisposableObserver<Object> observer = new
                DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object addNewModelResponse) {
                        getView().addNewModel(addNewModelResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgress();
                    }
                };
        AppApiService.getInstance().addingNewModel(merchantId, addNewModel).subscribe(observer);
        addDisposable(observer);
    }

}
