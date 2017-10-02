package com.incon.connect.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.incon.connect.R;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.databinding.ActivityLoginBinding;
import com.incon.connect.dto.login.LoginUserData;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.changepassword.ChangePasswordActivity;
import com.incon.connect.ui.forgotpassword.ForgotPasswordActivity;
import com.incon.connect.ui.home.HomeActivity;
import com.incon.connect.ui.register.RegistrationActivity;
import com.incon.connect.utils.SharedPrefsUtils;


public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = LoginActivity.class.getName();

    private ActivityLoginBinding binding;
    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initializePresenter() {
        loginPresenter = new LoginPresenter();
        loginPresenter.setView(this);
        setBasePresenter(loginPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);

//        LoginUserData loginUserData = new LoginUserData();
        LoginUserData loginUserData = new LoginUserData("7799879990", "password");
        String phoneNumberPreference = SharedPrefsUtils.loginProvider().
                getStringPreference(LoginPrefs.USER_PHONE_NUMBER);
        if (!TextUtils.isEmpty(phoneNumberPreference)) {
            loginUserData.setPhoneNumber(phoneNumberPreference);
            binding.edittextPassword.requestFocus();
        }
        binding.setUser(loginUserData);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.disposeAll();
    }

    @Override
    public void navigateToHomePage(LoginResponse loginResponse) {
        if (loginResponse == null) {
            clearData();
            return;
        }
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void navigateToChangePassword() {
        //removing entered password while navigating to changing password screen
        binding.edittextPassword.setText("");
        Intent changePasswordIntent = new Intent(this, ChangePasswordActivity.class);
        startActivity(changePasswordIntent);
    }

    public void onLoginClick() {

        LoginUserData loginUserData = binding.getUser();
        int validationRes = loginUserData.validateLogin();

        binding.inputLayoutUsername.setError(null);
        binding.inputLayoutPassword.setError(null);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        switch (validationRes) {
            case LoginValidation.PHONE_NUMBER_REQ:
                binding.inputLayoutUsername.setError(getString(R.string.error_phone_req));
                binding.inputLayoutUsername.startAnimation(shake);
                break;
            case LoginValidation.PHONE_NUMBER_NOTVALID:
                binding.inputLayoutUsername.setError(getString(R.string.error_phone_min_digits));
                binding.inputLayoutUsername.startAnimation(shake);
                break;
            case LoginValidation.PASSWORD_REQ:
                binding.inputLayoutPassword.setError(getString(R.string.error_password_req));
                binding.inputLayoutPassword.startAnimation(shake);
                break;
            default:
                loginPresenter.doLogin(binding.getUser());
                break;
        }
    }

    public void onRegisterClick() {
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        String phoneNumber = binding.edittextUsername.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)) {
            registrationIntent.putExtra(IntentConstants.USER_PHONE_NUMBER, phoneNumber);
        }
        startActivity(registrationIntent);
        // donot finish current activity, since user may come back to login screen from registration
        // screens
    }

    public void onForgotPasswordClick() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        String phoneNumber = binding.edittextUsername.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber)) {
            forgotPasswordIntent.putExtra(IntentConstants.USER_PHONE_NUMBER, phoneNumber);
        }
        startActivityForResult(forgotPasswordIntent, RequestCodes.FORGOT_PASSWORD);
        // donot finish current activity, since user may come back to login screen from registration
        // screens
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.FORGOT_PASSWORD:
                    binding.edittextPassword.setText("");
                    break;
                default:
                    break;
            }
        }
    }
}