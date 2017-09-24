package com.incon.connect.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.incon.connect.AppConstants;
import com.incon.connect.ConnectApplication;
import com.incon.connect.R;
import com.incon.connect.custom.exception.NoConnectivityException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorMsgUtil {


    private static final String TAG = ErrorMsgUtil.class.getSimpleName();

    @Deprecated
    public static String getErrorMsg(Throwable e) {
        Context appContext = ConnectApplication.getAppContext();

        Log.d(TAG, "observer ==> onError() obj = " + e);
        String errMsg = null;
        if (e instanceof NetworkErrorException) {
            errMsg = appContext.getString(R.string.error_network);
        } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            errMsg = appContext.getString(R.string.error_socket);
        } else if (e instanceof NoConnectivityException) {
            errMsg = appContext.getString(R.string.error_no_connectivity);
        } else if (e instanceof HttpException) {
            HttpException e1 = (HttpException) e;
            ResponseBody responseBody = e1.response().errorBody();
            Log.e(TAG, "onError = body : " + e1.response().body()
                    + " message " + e1.response().message()
                    + " code " + e1.response().code()
                    + " errorBody " + responseBody);
            try {
                JSONObject errorResponseBody = new JSONObject(responseBody.string());
                Log.e(TAG, "errorResponseBody = : " + errorResponseBody);
                errMsg = errorResponseBody.has("message")
                        ? errorResponseBody.getString("message")
                        : "";
            } catch (Exception ex) {
            }
        }
        return errMsg;
    }

    public static Pair<Integer, String> getErrorDetails(Throwable e) {
        Context appContext = ConnectApplication.getAppContext();

        int errorCode =
                Log.d(TAG, "observer ==> onError() obj = " + e);
        String errMsg = null;
        if (e instanceof NetworkErrorException) {
            errMsg = appContext.getString(R.string.error_network);
            errorCode = AppConstants.ErrorCodes.NETWORK_ERROR;
        } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            errMsg = appContext.getString(R.string.error_socket);
            errorCode = AppConstants.ErrorCodes.TIMEOUT;
        } else if (e instanceof NoConnectivityException) {
            errMsg = appContext.getString(R.string.error_no_connectivity);
            errorCode = AppConstants.ErrorCodes.NO_NETWORK;
        } else if (e instanceof HttpException) {
            HttpException e1 = (HttpException) e;
            ResponseBody responseBody = e1.response().errorBody();
            errorCode = e1.response().code();
            Logger.e(TAG, "onError = body : " + e1.response().body()
                    + " message " + e1.response().message()
                    + " code " + e1.response().code()
                    + " errorBody " + responseBody);
            try {
                JSONObject errorResponseBody = new JSONObject(responseBody.string());
                Logger.e(TAG, "errorResponseBody = : " + errorResponseBody);
                errMsg = errorResponseBody.has("message")
                        ? errorResponseBody.getString("message")
                        : "";
            } catch (Exception ex) {
            }
        }
        return new Pair<>(errorCode, errMsg);
    }

    public static int getStatusCode(Throwable e) {

        if (e instanceof HttpException) {
            HttpException e1 = (HttpException) e;
            ResponseBody responseBody = e1.response().errorBody();

            return e1.response().code();
        }

        return -1;

    }

    public static String getErrorMsg(ResponseBody responseBody) {
        try {
            JSONObject errorResponseBody = new JSONObject(responseBody.string());
            Logger.e(TAG, "errorResponseBody = : " + errorResponseBody);
            return errorResponseBody.has("message")
                    ? errorResponseBody.getString("message")
                    : "";
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
