package com.incon.connect.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.incon.connect.AppConstants;
import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.callbacks.AlertDialogCallback;
import com.incon.connect.custom.view.AppAlertVerticalTwoButtonsDialog;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.SharedPrefsUtils;

import net.hockeyapp.android.LoginActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.incon.connect.AppConstants.LoginPrefs.EMAIL_ID;

public abstract class BaseActivity extends AppCompatActivity implements BaseView,
        AppConstants {

    public static final int TRANSACTION_TYPE_ADD = 0;
    public static final int TRANSACTION_TYPE_REPLACE = 1;
    public static final int TRANSACTION_TYPE_REMOVE = 2;
    private AppAlertVerticalTwoButtonsDialog dialog;

    protected abstract int getLayoutId();
    protected abstract void initializePresenter();
    protected abstract void onCreateView(Bundle saveInstanceState);

    protected BasePresenter presenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());

        initializePresenter();

        onCreateView(savedInstanceState);
    }

    public void setBasePresenter(BasePresenter basePresenter) {
        presenter = basePresenter;
        if (presenter != null) {
            this.presenter.initialize(getIntent().getExtras());
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showProgress(String message) {
        hideProgress();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        AppUtils.showSnackBar(((ViewGroup) getWindow().getDecorView()
                .findViewById(android.R.id.content)).getChildAt(0), errorMessage);
    }

    @Override
    public void handleException(Pair<Integer, String> error) {
        if (error.first == 601) {
            updateAppDialog();
        }
        else if (error.first == 401 || error.first == 403) {
            Logger.e("handleException", "onLogoutCalled() from BA ");
            onLogoutClick();
        }
        else {
           showErrorMessage(error.second);
        }

    }

    public void onLogoutClick() {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();
        String emailId = sharedPrefsUtils.getStringPreference(EMAIL_ID);

        clearData();

        sharedPrefsUtils.setStringPreference(EMAIL_ID, emailId);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
    }

    public void clearData() {
//        TCDbHelper.getDbInstance().clearAllTables();

        SharedPrefsUtils.appProvider().clear();
        SharedPrefsUtils.cacheProvider().clear();
        SharedPrefsUtils.loginProvider().clear();
    }

    public void updateAppDialog() {
        dialog = new AppAlertVerticalTwoButtonsDialog.AlertDialogBuilder(this, new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                dialog.dismiss();
                                finish();
                                break;
                            case AlertDialogCallback.CANCEL:
                                dialog.dismiss();
                                finish();
                                openPlayStore();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.app_update))
                .button1Text(getString(R.string.action_cancel))
                .button2Text(getString(R.string.action_ok))
                .setButtonOrientation(LinearLayout.HORIZONTAL)
                .build();
        dialog.showDialog();
        dialog.setButtonBlueUnselectBackground();
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="
                            + appPackageName)));
        }
    }


}
