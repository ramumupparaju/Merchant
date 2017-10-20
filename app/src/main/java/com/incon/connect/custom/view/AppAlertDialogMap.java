package com.incon.connect.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.incon.connect.R;
import com.incon.connect.callbacks.AlertDialogCallback;

public class AppAlertDialogMap extends Dialog implements View.OnClickListener , OnMapReadyCallback {
    private final Context context;
    //All final attributes
    private final String title; // optional
    private final String content; // optional
    private final String button1text; // required
    private final String button2text; // required
    private final AlertDialogCallback mAlertDialogCallback; // required

    /**
     * @param builder
     */
    private AppAlertDialogMap(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
        this.content = builder.content;
        this.button1text = builder.button1Text;
        this.button2text = builder.button2Text;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dialog_map_app, null);
        Button mSecondButtonTextView = (Button) contentView.findViewById(R.id.dialog_second_button);
        if (!TextUtils.isEmpty(button2text)) {
            mSecondButtonTextView.setText(button2text);
        } else {
            mSecondButtonTextView.setVisibility(View.GONE);
        }
        mSecondButtonTextView.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    @Override
    public void onClick(View view) {
        if (mAlertDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.dialog_first_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.OK);
                break;
            case R.id.dialog_second_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.CANCEL);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public static class AlertDialogBuilder {

        private final Context context;
        private final AlertDialogCallback callback;
        private String title;
        private String content;
        private String button1Text;
        private String button2Text;

        public AlertDialogBuilder(Context context, AlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AlertDialogBuilder content(String content) {
            this.content = content;
            return this;
        }

        public AlertDialogBuilder button1Text(String button1Text) {
            this.button1Text = button1Text;
            return this;
        }

        public AlertDialogBuilder button2Text(String button2Text) {
            this.button2Text = button2Text;
            return this;
        }

        public AlertDialogBuilder downloadPreferenceHint(String downloadPreferenceHint) {
            return this;
        }

        //Return the finally constructed User object
        public AppAlertDialogMap build() {
            AppAlertDialogMap dialog = new AppAlertDialogMap(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }
}
