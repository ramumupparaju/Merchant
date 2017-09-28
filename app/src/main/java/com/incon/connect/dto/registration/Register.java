package com.incon.connect.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Register extends BaseObservable {

    private UserInfo userInfo;
    private StoreInfo storeInfo;

    @Bindable
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Bindable
    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }
}
