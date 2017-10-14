package com.incon.connect.ui.settings.update;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.incon.connect.R;
import com.incon.connect.callbacks.AlertDialogCallback;
import com.incon.connect.callbacks.TextAlertDialogCallback;
import com.incon.connect.custom.view.AppCheckBoxListDialog;
import com.incon.connect.custom.view.PickImageDialog;
import com.incon.connect.custom.view.PickImageDialogInterface;
import com.incon.connect.databinding.ActivityUpdateStoreProfileBinding;
import com.incon.connect.dto.dialog.CheckedModelSpinner;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.RegistrationMapActivity;
import com.incon.connect.ui.settings.SettingsActivity;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.PermissionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateStoreProfileActivity extends BaseActivity implements
        UpDateStoreProfileContract {
    ActivityUpdateStoreProfileBinding binding;
    private PickImageDialog pickImageDialog;
    private AppCheckBoxListDialog categoryDialog;
    private String selectedFilePath = "";
    private List<CheckedModelSpinner> categorySpinnerList;
    private static final String TAG = UpDateStoreProfileActivity.class.getSimpleName();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_store_profile;
    }

    @Override
    protected void initializePresenter() {

    }

    public void onSubmitClick() {
//        validateFields();

    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateStoreProfileActivity(this);
        binding.toolbarEditProfile.toolbarTextStore.setVisibility(android.view.View.VISIBLE);
        binding.toolbarEditProfile.toolbarTextUser.setVisibility(android.view.View.GONE);
        binding.toolbarEditProfile.toolbarLeftIv.setOnClickListener(
                new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent backToSettings = new Intent(UpDateStoreProfileActivity.this,
                        SettingsActivity.class);
                startActivity(backToSettings);
            }
        });
        binding.toolbarEditProfile.toolbarEditImage.setOnClickListener(
                new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                binding.buttonSubmit.setVisibility(android.view.View.VISIBLE);
                binding.toolbarEditProfile.toolbarEditImage.setVisibility(android.view.View.GONE);

            }
        });

    }

    public void openCameraToUpload() {
        PermissionUtils.getInstance().grantPermission(this,
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

    public void onAddressClick() {
        Intent addressIntent = new Intent(this, RegistrationMapActivity.class);
        startActivityForResult(addressIntent, RequestCodes.ADDRESS_LOCATION);
    }

    private void showImageOptionsDialog() {

        pickImageDialog = new PickImageDialog(this);
        pickImageDialog.mImageHandlingDelegate = pickImageDialogInterface;
        pickImageDialog.initDialogLayout();
    }
    public void onCategoryClick() {
        showCategorySelectionDialog();
    }

    private void showCategorySelectionDialog() {
        //set previous selected categories as checked
        String selectedCategories = binding.edittextUpDateCategory.getText().toString();
        if (!TextUtils.isEmpty(selectedCategories)) {
            String[] split = selectedCategories.split(COMMA_SEPARATOR);
            for (String categoryString : split) {
                CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
                checkedModelSpinner.setName(categoryString);
                int indexOf = categorySpinnerList.indexOf(checkedModelSpinner);
                categorySpinnerList.get(indexOf).setChecked(true);
            }

        }
        categoryDialog = new AppCheckBoxListDialog.AlertDialogBuilder(this, new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String caetogories) {
                        binding.edittextUpDateCategory.setText(caetogories);
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                categoryDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                categoryDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.register_category_hint))
                .spinnerItems(categorySpinnerList)
                .build();
        categoryDialog.showDialog();
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
            ((BaseActivity) UpDateStoreProfileActivity.this).loadImageUsingGlide(
                    selectedFilePath, binding.storeLogoIv);
        }
    };
}
