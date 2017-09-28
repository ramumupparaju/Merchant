package com.incon.connect.ui.register.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.AppConstants;
import com.incon.connect.R;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.termsandcondition.TermsAndConditionActivity;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class RegistrationStoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_store, container, false);
    }

    public void onClickNext() {
        Intent eulaIntent = new Intent(getActivity(), TermsAndConditionActivity.class);
        startActivityForResult(eulaIntent, AppConstants.RequestCodes.TERMS_AND_CONDITIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.RequestCodes.TERMS_AND_CONDITIONS && resultCode == Activity
                .RESULT_OK) {
            //TODO api cal for registration
            navigateToHome();
        }

    }

    public void navigateToHome() {
        /*PushPresenter pushPresenter = new PushPresenter();
        pushPresenter.pushRegisterApi();*/ //TODO enable

        Intent intent = new Intent(getActivity(),
                HomeActivity.class);
        // This is a convenient way to make the proper Intent to launch and
        // reset an application's task.
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        startActivity(mainIntent);
    }

}
