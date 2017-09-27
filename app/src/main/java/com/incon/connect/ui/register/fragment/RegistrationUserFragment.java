package com.incon.connect.ui.register.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.connect.R;
import com.incon.connect.ui.register.RegistrationActivity;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class RegistrationUserFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_user, container, false);
    }

    public void onClickNext() {
        ((RegistrationActivity) getActivity()).navigateToNext();
    }
}
