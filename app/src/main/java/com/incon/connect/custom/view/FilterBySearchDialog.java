package com.incon.connect.custom.view;

import android.app.Activity;
import android.content.DialogInterface;

import com.incon.connect.R;

/**
 * Created by PC on 10/17/2017.
 */

public class FilterBySearchDialog {
    private static final String TAG = FilterBySearchDialog.class.getSimpleName();
    private Activity mActivity;

    public FilterBySearchDialog(Activity activity) {
        this.mActivity = activity;
    }


    public void  initDialogLayout() {
        CharSequence[] items = mActivity.getResources().getStringArray(R.array.select_search);
        int themeMaterialDialogAlert = android.R.style.Theme_Material_Light_Dialog_Alert;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            themeMaterialDialogAlert = android.R.style
                    .Theme_DeviceDefault_Light_Dialog;
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity,
                themeMaterialDialogAlert);
        //builder.setTitle(getString(R.string.app_name));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                dialogInterface.dismiss();

            }
        });
        builder.setCancelable(true);
        builder.create().show();

    }
}
