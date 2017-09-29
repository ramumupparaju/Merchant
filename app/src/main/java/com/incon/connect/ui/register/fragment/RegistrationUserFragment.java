package com.incon.connect.ui.register.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import com.incon.connect.R;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.FragmentRegistrationUserBinding;
import com.incon.connect.dto.registration.Register;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.register.RegistrationActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationUserFragment extends BaseFragment implements
        RegistrationUserStoreFragmentContract.View {

    private RegistrationUserFragmentPresenter registrationUserInfoFragPresenter;
    private FragmentRegistrationUserBinding binding;
    private Register register; // initialized from registration acticity
    private Animation shakeAnim;
    private HashMap<Integer, String> errorMap;
    private MaterialBetterSpinner genderSpinner;

    @Override
    protected void initializePresenter() {

        registrationUserInfoFragPresenter = new RegistrationUserFragmentPresenter();
        registrationUserInfoFragPresenter.setView(this);
        setBasePresenter(registrationUserInfoFragPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_registration_user, container, false);
        View rootView = binding.getRoot();
        //here data must be an instance of the registration class
        register = ((RegistrationActivity) getActivity()).registrationPresenter.getRegister();

        registrationUserInfoFragPresenter.registerUserInfo(register.getUserInfo());
        binding.setRegister(register);

        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        loadGenderSpinnerData();
        loadValidationErrors();
        setFocusListenersForEditText();
        return rootView;
    }

    void loadGenderSpinnerData() {
        final List<String> genderTypeList = new ArrayList<>();
        genderTypeList.add("Male");
        genderTypeList.add("Female");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        genderSpinner = binding.spinnerGender;
        genderSpinner.setAdapter(arrayAdapter);

    }

    private void loadValidationErrors() {

        errorMap = new HashMap<>();
        errorMap.put(RegistrationValidation.NAME_REQ,
                getString(R.string.error_name_req));

        errorMap.put(RegistrationValidation.PHONE_REQ,
                getString(R.string.error_phone_req));

        errorMap.put(RegistrationValidation.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));

        errorMap.put(RegistrationValidation.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(RegistrationValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(RegistrationValidation.PASSWORD_REQ,
                getString(R.string.error_password_req));

        errorMap.put(RegistrationValidation.PASSWORD_PATTERN_REQ,
                getString(R.string.error_password_pattern_req));

        errorMap.put(RegistrationValidation.RE_ENTER_PASSWORD_REQ,
                getString(R.string.error_re_enter_password_req));

        errorMap.put(RegistrationValidation.RE_ENTER_PASSWORD_DOES_NOT_MATCH,
                getString(R.string.error_re_enter_password_does_not_match));

        errorMap.put(RegistrationValidation.GENDER_REQ,
                getString(R.string.error_gender_req));

    }

    private void setFocusListenersForEditText() {

        /*TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_register_name:
                                    binding.edittextRegisterPhone.requestFocus();
                                    break;

                                case R.id.edittext_register_phone:
                                    binding.spinnerPhoneType.requestFocus();
                                    binding.spinnerPhoneType.showDropDown();
                                    break;

                                case R.id.edittext_register_reenter_password:
                                    binding.spinnerTimezone.requestFocus();
                                    binding.spinnerTimezone.showDropDown();
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };

        binding.edittextRegisterPhone.setOnEditorActionListener(onEditorActionListener);
        binding.edittextRegisterReenterPassword.setOnEditorActionListener(onEditorActionListener);*/

        binding.edittextRegisterUserName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterPhone.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterEmailid.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterPassword.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterReenterPassword.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = registrationUserInfoFragPresenter
                        .validateUserInfo((String) fieldId);
                if (!hasFocus) {
                    if (view instanceof TextInputEditText) {
                        TextInputEditText textInputEditText = (TextInputEditText) view;
                        textInputEditText.setText(textInputEditText.getText().toString().trim());
                    }
                    updateUiAfterValidation(validation.first, validation.second);
                }
            }
        }
    };

    private void updateUiAfterValidation(String tag, int validationId) {

        if (tag == null) {
            return;
        }

        View viewByTag = binding.getRoot().findViewWithTag(tag);
        setFieldError(viewByTag, validationId);

    }

    private void setFieldError(View view, int validationId) {

        if (view instanceof TextInputEditText) {
            ((CustomTextInputLayout) view.getParent().getParent())
                    .setError(validationId == VALIDATION_SUCCESS ? null
                            : errorMap.get(validationId));
        } else {
            ((MaterialBetterSpinner) view).setError(validationId == VALIDATION_SUCCESS ? null
                    : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
            ((RegistrationActivity) getActivity()).focusOnView(binding.scrollviewUserInfo, view);
        }
    }

    /**
     * called from registration activity when user click on next in bottom bars
     * validate user , if all fields ok then call next screen
     */
    public void onClickNext() {
        String genderText = genderSpinner.getText().toString();
        /*for (RegistrationTimezone registrationTimezone : timezone) {
            if (registrationTimezone.getName().equals(timeZoneText)) {
                register.getRegistrationBody().setTimeZone(registrationTimezone.getId());
            }
        }*/
        if (validateFields()) {
            navigateToRegistrationActivityNext();
        }
    }

    private boolean validateFields() {
        binding.inputLayoutRegisterUserName.setError(null);
        binding.inputLayoutRegisterPhone.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.inputLayoutRegisterPassword.setError(null);
        binding.inputLayoutRegisterReenterPassword.setError(null);
        binding.spinnerGender.setError(null);

        Pair<String, Integer> validation = registrationUserInfoFragPresenter.validateUserInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    @Override
    public void navigateToRegistrationActivityNext() {
        ((RegistrationActivity) getActivity()).navigateToNext();
    }
}
