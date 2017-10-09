package com.incon.connect.ui.buyrequets;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.apimodel.components.buyrequest.BuyRequestResponse;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;


/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class BuyRequestPresenter extends BasePresenter<BuyRequestContract.View> implements
        BuyRequestContract.Presenter {

    private static final String TAG = BuyRequestPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    public void buyRequest(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_buy_request));
        DisposableObserver<List<BuyRequestResponse>> observer = new
                DisposableObserver<List<BuyRequestResponse>>() {
            @Override
            public void onNext(List<BuyRequestResponse> buyRequestResponseList) {
                getView().loadBuyRequest(buyRequestResponseList);
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
        AppApiService.getInstance().buyRequestApi(userId).subscribe(observer);
        addDisposable(observer);
    }



}
