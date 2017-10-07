package com.incon.connect.ui.changepassword;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.api.AppApiService;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.View> implements
        ChangePasswordContract.Presenter {

    private static final String TAG = ChangePasswordPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void changePassword(HashMap<String, String> password) {
        getView().showProgress(appContext.getString(R.string.progress_changepassword));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().navigateToLoginPage();
            }
            @Override
            public void onError(Throwable e) {
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        AppApiService.getInstance().changePassword(password).subscribe(observer);
        addDisposable(observer);
    }

}
