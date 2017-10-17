package com.incon.connect.ui.settings.update;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.databinding.ActivityUpdateUserProfileBinding;
import com.incon.connect.dto.update.UpDateUserProfile;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.RegistrationMapActivity;
import com.incon.connect.ui.settings.SettingsActivity;
import com.incon.connect.utils.DateUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import static com.incon.connect.AppConstants.DateFormatterConstants.MM_DD_YYYY;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateUserProfileActivity extends BaseActivity implements
        UpDateUserProfileContract.View {
    ActivityUpdateUserProfileBinding binding;
    private HashMap<Integer, String> errorMap;
    private  UpDateUserProfile dateUserProfile;
    private MaterialBetterSpinner genderSpinner;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_profile;
    }

    @Override
    protected void initializePresenter() {

    }


    public void onSubmitClick() {
//        validateFields();

    }

    public void onAddressClick() {
        Intent addressIntent = new Intent(this, RegistrationMapActivity.class);
        startActivityForResult(addressIntent, RequestCodes.ADDRESS_LOCATION);
    }

    public void onDobClick() {
        showDatePicker();
    }


    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateUserProfileActivity(this);
        binding.toolbarEditProfile.toolbarTextStore.setVisibility(View.GONE);
        binding.toolbarEditProfile.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backToSettings = new Intent(UpDateUserProfileActivity.this,
                        SettingsActivity.class);
                startActivity(backToSettings);
            }
        });
        binding.toolbarEditProfile.toolbarEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonSubmit.setVisibility(View.VISIBLE);
                binding.toolbarEditProfile.toolbarEditImage.setVisibility(View.GONE);

            }
        });
        loadData();

    }

    private void loadData() {

        loadValidationErrors();
        loadGenderSpinnerData();

    }

    private void showDatePicker() {

        AppUtils.hideSoftKeyboard(this, binding.viewDob);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());


        String dateOfBirth = dateUserProfile.getDateOfBirthToShow();
        if (!TextUtils.isEmpty(dateOfBirth)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    dateOfBirth, DateFormatterConstants.MM_DD_YYYY));
        }

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;

        DatePickerDialog datePicker = new DatePickerDialog(this,
                customStyle,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }
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
                    binding.edittextUpDateDob.setText(strDt);
                    String dobInYYYYMMDD = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.YYYY_MM_DD);
                    dateUserProfile.setDob(dobInYYYYMMDD);
                    dateUserProfile.setDateOfBirthToShow(strDt);

                    Pair<String, Integer> validate = binding.getUpDateUserProfile().
                            validateUserInfo((String) binding.edittextUpDateDob.getTag());
                    //updateUiAfterValidation(validate.first, validate.second);
                }
            };

    void loadGenderSpinnerData() {
        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
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

        errorMap.put(RegistrationValidation.ADDRESS_REQ, getString(R.string.error_address_req));


    }
}
