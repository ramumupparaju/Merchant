package com.incon.connect.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/*import com.incon.connect.MainActivity;*/
import com.incon.connect.R;
import com.incon.connect.apimodel.components.login.LoginResponse;
import com.incon.connect.custom.view.AppAlertDialog;
import com.incon.connect.custom.view.AppAlertVerticalTwoButtonsDialog;
import com.incon.connect.databinding.ActivityLoginBinding;
import com.incon.connect.dto.login.User;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.changepassword.ChangePasswordActivity;
import com.incon.connect.ui.forgotpassword.ForgotPasswordActivity;
import com.incon.connect.ui.homescan.HomeScanActivity;
import com.incon.connect.utils.SharedPrefsUtils;

import static com.incon.connect.AppConstants.ActivityResult.IS_REGISTRATION_SUCCESS;
import static com.incon.connect.AppConstants.RequestCodes.REGISTRATION;


public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginPresenter loginPresenter;
    private ActivityLoginBinding binding;

    private static final String TAG = LoginActivity.class.getName();
    private AppAlertVerticalTwoButtonsDialog dialog;
    private AppAlertDialog singleButtondialog;

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

//        User user = new User();
        User user = new User("7799879990", "123456");
        String emailId = SharedPrefsUtils.loginProvider().
                getStringPreference(LoginPrefs.EMAIL_ID);
        if (!TextUtils.isEmpty(emailId)) {
            user.setEmail(emailId);
            binding.edittextPassword.requestFocus();
        }
        binding.setUser(user);

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
        /*if (loginResponse == null) {
            clearData();
            return;
        }

        loginPresenter.saveLoginData(loginResponse);*/

/*
        PushPresenter pushPresenter = new PushPresenter();
        pushPresenter.pushRegisterApi();
*/
        loginPresenter.setLoginStatus(true);
        Intent homeIntent = new Intent(this, HomeScanActivity.class);
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

        User user = binding.getUser();
        int validationRes = user.validateLogin();

        binding.inputLayoutUsername.setError(null);
        binding.inputLayoutPassword.setError(null);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        switch (validationRes) {
            /*case LoginValidation.EMAIL_REQ:
                binding.inputLayoutUsername.setError(getString(R.string.error_email_req));
                binding.inputLayoutUsername.startAnimation(shake);
                break;
            case LoginValidation.EMAIL_NOTVALID:
                binding.inputLayoutUsername.setError(getString(R.string.error_email_notvalid));
                binding.inputLayoutUsername.startAnimation(shake);
                break;
            case LoginValidation.PASSWORD_REQ:
                binding.inputLayoutPassword.setError(getString(R.string.error_password_req));
                binding.inputLayoutPassword.startAnimation(shake);
                break;*/
            default:
                loginPresenter.doLogin(binding.getUser());
                break;
        }
    }

    public void onRegisterClick() {
        /*Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        String emailAddress = binding.edittextUsername.getText().toString();
        if (!TextUtils.isEmpty(emailAddress)) {
            registrationIntent.putExtra(IntentConstants.EMAIL_ADDRESS, emailAddress);
        }
        startActivityForResult(registrationIntent, REGISTRATION);*/
        // donot finish current activity, since user may come back to login screen from registration
        // screens
    }

    public void onForgotPasswordClick() {
        Intent forgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        forgotPasswordIntent.putExtra("emailId",
                binding.edittextUsername.getEditableText().toString());
        startActivityForResult(forgotPasswordIntent, RequestCodes.FORGOT_PASSWORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.FORGOT_PASSWORD:
                    binding.edittextPassword.setText("");
                    break;
                case REGISTRATION:

                    if (data != null) {
                        boolean isRegistrationSuccess =
                                data.getBooleanExtra(IS_REGISTRATION_SUCCESS, false);
                        if (isRegistrationSuccess) {

                            String email = SharedPrefsUtils.loginProvider().
                                    getStringPreference(LoginPrefs.EMAIL_ID);
                            binding.getUser().setEmail(email);
                            binding.edittextUsername.setText(email);
                        }
                    }
                    else {
                        showErrorMessage(getString(R.string.error_no_connectivity));
                    }
                    break;

                default:
                    break;
            }
        }
    }
}