package com.incon.connect.ui.history.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.history.purchased.ReturnHistoryResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 10/2/2017.
 */

public class ReturnPresenter extends BasePresenter<ReturnContract.View> implements
        ReturnContract.Presenter {
    private static final String TAG = ReturnPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }


     public void returnH(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_return_history));
        DisposableObserver<List<ReturnHistoryResponse>> observer = new
                DisposableObserver<List<ReturnHistoryResponse>>() {
                    @Override
                    public void onNext(List<ReturnHistoryResponse> historyResponse) {
                        getView().loadReturnHistory(historyResponse);
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
        AppApiService.getInstance().returnApi(userId).subscribe(observer);
        addDisposable(observer);
    }


}
