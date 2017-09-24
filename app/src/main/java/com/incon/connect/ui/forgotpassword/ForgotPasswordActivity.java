package com.incon.connect.ui.forgotpassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityForgotPasswordBinding;
import com.incon.connect.databinding.ViewRegistrationBottomButtonsBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.resetpassword.ResetPasswordPromptActivity;
import com.incon.connect.utils.Validator;

import java.util.HashMap;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View,
        View.OnClickListener {

    ForgotPasswordPresenter forgotPasswordPresenter;
    ActivityForgotPasswordBinding binding;
    private ViewRegistrationBottomButtonsBinding buttonsBinding;

    private static final String TAG = ForgotPasswordActivity.class.getName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initializePresenter() {
        forgotPasswordPresenter = new ForgotPasswordPresenter();
        forgotPasswordPresenter.setView(this);
        setBasePresenter(forgotPasswordPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        buttonsBinding = binding.includeRegisterBottomButtons;

        String emailId = getIntent().getExtras().getString("emailId");
        if (emailId != null && emailId.length() > 0) {
            binding.edittextUsername.setText(emailId);
            binding.edittextUsername.setSelection(emailId.length());
        }

        setBottomButtonViews();
        setButtonClickListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        forgotPasswordPresenter.disposeAll();
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
    public void navigateToResetPromtPage() {
        Intent registrationIntent = new Intent(this, ResetPasswordPromptActivity.class);
        startActivity(registrationIntent);
        finish();
    }

    @Override
    public boolean validateEmail() {
        boolean isValid = true;
        binding.inputLayoutUsername.setError(null);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        String email = binding.edittextUsername.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.inputLayoutUsername.setError(getString(R.string.error_email_req));
            binding.inputLayoutUsername.startAnimation(shake);
            isValid = false;
        } else if (!Validator.isValidEmail(email)) {
            binding.inputLayoutUsername.setError(getString(R.string.error_email_notvalid));
            binding.inputLayoutUsername.startAnimation(shake);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                finish();
                break;
            case R.id.button_right:
                boolean validateMsg = validateEmail();
                if (validateMsg) {
                    HashMap<String, String> emailMap = new HashMap<>();
                    emailMap.put(ApiRequestKeyConstants.BODY_EMAIL,
                            binding.edittextUsername.getText().toString());
                    forgotPasswordPresenter.forgotPassword(emailMap);
                }
                break;
            default:
                break;
        }
    }

    private void setBottomButtonViews() {
        buttonsBinding.buttonLeft.setText(R.string.action_back);
        buttonsBinding.buttonRight.setText(R.string.action_reset_password);
    }

    private void setButtonClickListeners() {
        buttonsBinding.buttonLeft.setOnClickListener(this);
        buttonsBinding.buttonRight.setOnClickListener(this);
    }

}
