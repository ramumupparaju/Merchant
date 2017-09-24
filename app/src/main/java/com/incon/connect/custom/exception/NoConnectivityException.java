package com.incon.connect.custom.exception;

import com.incon.connect.ConnectApplication;
import com.incon.connect.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return ConnectApplication.getAppContext().getString(R.string.msg_check_internet);
    }
}
