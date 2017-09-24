package com.incon.connect.ui.resetpassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.incon.connect.R;
import com.incon.connect.databinding.ActivityResetPasswordPromptBinding;
import com.incon.connect.ui.BaseActivity;

public class ResetPasswordPromptActivity extends BaseActivity {

    private static final String TAG = ResetPasswordPromptActivity.class.getName();
    ActivityResetPasswordPromptBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password_prompt;
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setResetPasswordPrompt(this);
    }

    @Override
    public void showProgress(String message) {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    public void onOkClick() {
        finish();
    }
}
