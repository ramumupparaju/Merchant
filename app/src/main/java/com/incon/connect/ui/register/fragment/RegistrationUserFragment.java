package com.incon.connect.ui.register.fragment;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.FragmentRegistrationUserBinding;
import com.incon.connect.dto.registration.Register;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.register.RegistrationActivity;
import com.incon.connect.utils.DateUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import static com.incon.connect.AppConstants.DateFormatterConstants.MM_DD_YYYY;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationUserFragment extends BaseFragment implements
        RegistrationUserFragmentContract.View {

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
        binding.setUserFragment(this);
        //here data must be an instance of the registration class
        register = ((RegistrationActivity) getActivity()).registrationPresenter.getRegister();
        binding.setRegister(register);
        View rootView = binding.getRoot();

        loadData();
        return rootView;
    }

    private void loadData() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        loadGenderSpinnerData();
        loadValidationErrors();
        setFocusListenersForEditText();
    }

    public void onDobClick() {
        showDatePicker();
    }

    private void showDatePicker() {
        AppUtils.hideSoftKeyboard(getActivity(), getView());
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());


        String dateOfBirth = register.getUserInfo().getDateOfBirthToShow();
        if (!TextUtils.isEmpty(dateOfBirth)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    dateOfBirth, DateFormatterConstants.MM_DD_YYYY));
        }

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                customStyle,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }

    // Date Listener
    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay);

                    SimpleDateFormat simpleDate = new SimpleDateFormat(MM_DD_YYYY,
                            Locale.getDefault());
                    String strDt = simpleDate.format(selectedDateTime.getTime());
                    binding.edittextRegisterDob.setText(strDt);
                    String dobInYYYYMMDD = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.YYYY_MM_DD);
                    register.getUserInfo().setDob(dobInYYYYMMDD);
                    register.getUserInfo().setDateOfBirthToShow(strDt);

                    Pair<String, Integer> validate = binding.getRegister().getUserInfo()
                            .validateUserInfo((String) binding.edittextRegisterDob.getTag());
                    updateUiAfterValidation(validate.first, validate.second);
                }
            };

    void loadGenderSpinnerData() {
        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

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

        errorMap.put(RegistrationValidation.GENDER_REQ,
                getString(R.string.error_gender_req));

        errorMap.put(RegistrationValidation.DOB_REQ,
                getString(R.string.error_dob_req));

        errorMap.put(RegistrationValidation.DOB_FUTURE_DATE,
                getString(R.string.error_dob_futuredate));

        errorMap.put(RegistrationValidation.DOB_PERSON_LIMIT,
                getString(R.string.error_dob_patient_is_user));

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

    }

    private void setFocusListenersForEditText() {

        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_register_phone:
                                    binding.spinnerGender.requestFocus();
                                    binding.spinnerGender.showDropDown();
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };

        binding.edittextRegisterPhone.setOnEditorActionListener(onEditorActionListener);

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
                Pair<String, Integer> validation = binding.getRegister().getUserInfo()
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
        if (validateFields()) {
            navigateToRegistrationActivityNext();
        }/* else {
            navigateToRegistrationActivityNext(); // TODO have to comment
        }*/
    }

    private boolean validateFields() {
        binding.inputLayoutRegisterUserName.setError(null);
        binding.inputLayoutRegisterPhone.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.inputLayoutRegisterPassword.setError(null);
        binding.inputLayoutRegisterConfirmPassword.setError(null);
        binding.spinnerGender.setError(null);

        Pair<String, Integer> validation = binding.getRegister().getUserInfo()
                .validateUserInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    @Override
    public void navigateToRegistrationActivityNext() {
        ((RegistrationActivity) getActivity()).navigateToNext();
    }
}
