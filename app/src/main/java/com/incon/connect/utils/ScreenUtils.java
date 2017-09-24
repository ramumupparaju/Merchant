package com.incon.connect.utils;

import android.util.TypedValue;

import com.incon.connect.ConnectApplication;

public class ScreenUtils {

    public static float convertPxToDp(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                ConnectApplication.getAppContext().getResources().getDisplayMetrics());
    }

}
