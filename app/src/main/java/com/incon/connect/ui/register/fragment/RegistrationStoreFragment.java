package com.incon.connect.ui.register.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.IntentCompat;
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
import android.widget.TextView;

import com.incon.connect.AppConstants;
import com.incon.connect.R;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.custom.view.PickImageDialogInterface;
import com.incon.connect.databinding.FragmentRegistrationStoreBinding;
import com.incon.connect.dto.registration.Registration;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.BaseFragment;
import com.incon.connect.ui.RegistrationMapActivity;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.register.RegistrationActivity;
import com.incon.connect.ui.termsandcondition.TermsAndConditionActivity;
import com.incon.connect.custom.view.PickImageDialog;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.PermissionUtils;
import com.incon.connect.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.incon.connect.AppConstants.ApiRequestKeyConstants.STORE_LOGO;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationStoreFragment extends BaseFragment implements
        RegistrationStoreFragmentContract.View {

    private static final String TAG = RegistrationStoreFragment.class.getSimpleName();
    private RegistrationStoreFragmentPresenter registrationStoreFragmentPresenter;
    private FragmentRegistrationStoreBinding binding;
    private Registration register; // initialized from registration acticity
    private Animation shakeAnim;
    private HashMap<Integer, String> errorMap;
    private PickImageDialog pickImageDialog;
    private String selectedFilePath = "";

    @Override
    protected void initializePresenter() {
        registrationStoreFragmentPresenter = new RegistrationStoreFragmentPresenter();
        registrationStoreFragmentPresenter.setView(this);
        setBasePresenter(registrationStoreFragmentPresenter);
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_registration_store, container, false);
        binding.setStoreFragment(this);
        //here data must be an instance of the registration class
        register = ((RegistrationActivity) getActivity()).getRegistration();
        binding.setRegister(register);
        View rootView = binding.getRoot();

        loadData();
        return rootView;
    }

    private void loadData() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        loadCategorySpinnerData();
        loadValidationErrors();
        setFocusListenersForEditText();
    }


    public void openCameraToUpload() {
        PermissionUtils.getInstance().grantPermission(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                new PermissionUtils.Callback() {
                    @Override
                    public void onFinish(HashMap<String, Integer> permissionsStatusMap) {
                        int storageStatus = permissionsStatusMap.get(
                                Manifest.permission.CAMERA);
                        switch (storageStatus) {
                            case PermissionUtils.PERMISSION_GRANTED:
                                showImageOptionsDialog();
                                Logger.v(TAG, "location :" + "granted");
                                break;
                            case PermissionUtils.PERMISSION_DENIED:
                                Logger.v(TAG, "location :" + "denied");
                                break;
                            case PermissionUtils.PERMISSION_DENIED_FOREVER:
                                Logger.v(TAG, "location :" + "denied forever");
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    private void showImageOptionsDialog() {
        pickImageDialog = new PickImageDialog(getActivity());
        pickImageDialog.mImageHandlingDelegate = pickImageDialogInterface;
        pickImageDialog.initDialogLayout();
    }


    private PickImageDialogInterface pickImageDialogInterface = new PickImageDialogInterface() {
        @Override
        public void handleIntent(Intent intent, int requestCode) {
            if (requestCode == RequestCodes.SEND_IMAGE_PATH) { // loading image in full screen
                if (TextUtils.isEmpty(selectedFilePath)) {
                    showErrorMessage(getString(R.string.error_image_path_req));
                } else {
                    intent.putExtra(IntentConstants.IMAGE_PATH, selectedFilePath);
                    startActivity(intent);
                }
                return;
            }
            startActivityForResult(intent, requestCode);
        }

        @Override
        public void displayPickedImage(String uri, int requestCode) {
            selectedFilePath = uri;
            ((BaseActivity) getActivity()).loadImageUsingGlide(
                    selectedFilePath, binding.storeLogoIv);
        }
    };

    void loadCategorySpinnerData() {
        //TODO have to change using api
        String[] categoryTypeList = getResources().getStringArray(R.array.category_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, categoryTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerCategory.setAdapter(arrayAdapter);
    }

    private void loadValidationErrors() {

        errorMap = new HashMap<>();
        errorMap.put(RegistrationValidation.NAME_REQ,
                getString(R.string.error_name_req));

        errorMap.put(RegistrationValidation.PHONE_REQ,
                getString(R.string.error_phone_req));

        errorMap.put(RegistrationValidation.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));

        errorMap.put(RegistrationValidation.CATEGORY_REQ,
                getString(R.string.error_category_req));

        errorMap.put(RegistrationValidation.ADDRESS_REQ,
                getString(R.string.error_address_req));

        errorMap.put(RegistrationValidation.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(RegistrationValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(RegistrationValidation.GSTN_REQ,
                getString(R.string.error_gstn_req));
    }


    private void setFocusListenersForEditText() {

        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_register_phone:
                                    binding.spinnerCategory.requestFocus();
                                    binding.spinnerCategory.showDropDown();
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };

        binding.edittextRegisterPhone.setOnEditorActionListener(onEditorActionListener);

        binding.edittextRegisterStoreName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterPhone.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterEmailid.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = register.validateStoreInfo((String) fieldId);
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
            ((RegistrationActivity) getActivity()).focusOnView(binding.scrollviewStoreInfo, view);
        }
    }

    public void onAddressClick() {
        Intent addressIntent = new Intent(getActivity(), RegistrationMapActivity.class);
        startActivity(addressIntent);
        binding.edittextRegisterAddress.setText("addrees");
    }

    /**
     * called from registration activity when user click on next in bottom bars
     * validate user , if all fields ok then call next screen
     */
    public void onClickNext() {
        if (validateFields()) {
            if (TextUtils.isEmpty(selectedFilePath)) {
                showErrorMessage(getString(R.string.error_image_path_upload));
                return;
            }
            navigateToRegistrationActivityNext();
        }
    }

    private boolean validateFields() {
        binding.inputLayoutRegisterStoreName.setError(null);
        binding.inputLayoutRegisterPhone.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.spinnerCategory.setError(null);

        Pair<String, Integer> validation = register.validateStoreInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    @Override
    public void navigateToRegistrationActivityNext() {
        Intent eulaIntent = new Intent(getActivity(), TermsAndConditionActivity.class);
        startActivityForResult(eulaIntent, AppConstants.RequestCodes.TERMS_AND_CONDITIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TERMS_AND_CONDITIONS:
                    registrationStoreFragmentPresenter.register(register);
                    break;
                default:
                    pickImageDialog.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

    }

    public void navigateToHomeScreen() {
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

    @Override
    public void uploadStoreLogo() {
        File fileToUpload = new File(selectedFilePath == null ? "" : selectedFilePath);

        // create RequestBody instance from file
        MultipartBody.Part imagenPerfil = null;
        if (fileToUpload.exists()) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), fileToUpload);
            // MultipartBody.Part is used to send also the actual file name
            imagenPerfil = MultipartBody.Part.createFormData(STORE_LOGO,
                    fileToUpload.getName(), requestFile);
        } else {
            showErrorMessage(getString(R.string.error_image_path_upload));
            return;
        }
        registrationStoreFragmentPresenter.uploadStoreLogo(SharedPrefsUtils.loginProvider()
                .getIntegerPreference(LoginPrefs.STORE_ID, 0), imagenPerfil);
    }

}
