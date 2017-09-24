package com.incon.connect.ui.changepassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.incon.connect.R;
import com.incon.connect.custom.view.CustomTextInputLayout;
import com.incon.connect.databinding.ActivityChangePasswordBinding;
import com.incon.connect.dto.changepassword.Password;
import com.incon.connect.ui.BaseActivity;

import java.util.HashMap;


public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {
    private static final String TAG = ChangePasswordActivity.class.getName();
    private ChangePasswordPresenter changePasswordPresenter;
    private ActivityChangePasswordBinding binding;

    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initializePresenter() {
        changePasswordPresenter = new ChangePasswordPresenter();
        changePasswordPresenter.setView(this);
        setBasePresenter(changePasswordPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        Password password = new Password();
        binding.setPassword(password);
        binding.setActivity(this);

        changePasswordPresenter.setChangePassword(password);

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadValidationErrors();
        setFocusForViews();
    }

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(PasswordValidation.NEWPWD_REQ, getString(R.string.error_newpwd_req));

        errorMap.put(PasswordValidation.NEWPWD_PATTERN_REQ,
                getString(R.string.error_password_pattern_req));

        errorMap.put(PasswordValidation.CONFIRMPWD_REQ,
                getString(R.string.error_confirmpwd_req));

        errorMap.put(PasswordValidation.PWD_NOTMATCH,
                getString(R.string.error_pwd_notmatched));
    }

    private void setFocusForViews() {
        binding.edittextEnterNewpassword.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextConfirmPassword.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = changePasswordPresenter
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changePasswordPresenter.disposeAll();
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    public void navigateToLoginPage() {
        finish();
    }

    private boolean validateFields() {
        binding.inputLayoutEnterNewpassword.setError(null);
        binding.inputLayoutConfirmPassword.setError(null);

        Pair<String, Integer> validation = changePasswordPresenter.validateUserInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    public void onChangePasswordClick() {
        if (validateFields()) {
            HashMap<String, String> passwordMap = new HashMap<>();
            passwordMap.put(ApiRequestKeyConstants.BODY_PASSWORD,
                    binding.getPassword().getConfirmPassword());
            changePasswordPresenter.resetPassword(passwordMap);
        }
    }

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
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }
}
