package com.incon.connect.ui.notifications;

import android.os.Bundle;
import android.text.TextUtils;

import com.incon.connect.AppConstants;
import com.incon.connect.ui.BasePresenter;
import com.incon.connect.utils.SharedPrefsUtils;

/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class PushPresenter extends BasePresenter<PushContract.View> implements
        PushContract.Presenter {

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
    }


    @Override
    public void pushRegisterApi() {

        if (TextUtils.isEmpty(SharedPrefsUtils.loginProvider()
                .getStringPreference(AppConstants.LoginPrefs.USER_ID))) {
            return;
        }

        //TODO enable
       /* PushRegistrarBody pushRegistrarBody = new PushRegistrarBody();

        pushRegistrarBody.setuId(DeviceUtils.getUniqueID());
        pushRegistrarBody.setPushKey(FirebaseInstanceId.getInstance().getToken());
        pushRegistrarBody.setOsVersion(String.valueOf(Build.VERSION.SDK_INT));
        pushRegistrarBody.setManufacturer(Build.MANUFACTURER);
        pushRegistrarBody.setLocale(TimeZone.getDefault().getID());
        pushRegistrarBody.setModel(Build.MODEL);
        pushRegistrarBody.setDeviceType(AppConstants.PushSubTypeConstants.PUSH_DEVICE_TYPE);

        DisposableObserver<Object> observer =
                new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object object) {
                        SharedPrefsUtils.cacheProvider().setBooleanPreference(AppConstants
                                .LoginPrefs.PUSH_TOKEN_STATUS, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };

        AppApiService.getInstance().pushTokenApi(pushRegistrarBody).subscribe(observer);
        addDisposable(observer);*/
    }

}
