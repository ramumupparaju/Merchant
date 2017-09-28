package com.incon.connect.ui.settings;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.connect.BuildConfig;
import com.incon.connect.R;
import com.incon.connect.callbacks.AlertDialogCallback;
import com.incon.connect.callbacks.IClickCallback;
import com.incon.connect.custom.view.AppAlertVerticalTwoButtonsDialog;
import com.incon.connect.databinding.ActivitySettingsBinding;
import com.incon.connect.dto.settings.SettingsItem;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.changepassword.ChangePasswordActivity;
import com.incon.connect.ui.register.RegistrationActivity;
import com.incon.connect.ui.settings.adapters.SettingsAdapter;

import java.util.ArrayList;

/**
 * Created on 26 Jul 2017 3:47 PM.
 *
 */
public class SettingsActivity extends BaseActivity implements SettingsContract.View,
        IClickCallback {

    private SettingsPresenter menuPresenter;
    private ActivitySettingsBinding binding;
    private SettingsAdapter menuAdapter;
    private ArrayList<SettingsItem> menuItems;
    private AppAlertVerticalTwoButtonsDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initializePresenter() {
        menuPresenter = new SettingsPresenter();
        menuPresenter.setView(this);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        binding.appVersion.setText(getString(R.string.app_version) + BuildConfig.VERSION_NAME);
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initViews();
        initializeAdapter();
        prepareMenuData();
        menuAdapter.setData(menuItems);
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        binding.recyclerviewMenu.addItemDecoration(dividerItemDecoration);
        binding.recyclerviewMenu.setLayoutManager(linearLayoutManager);
    }

    private void initializeAdapter() {
        menuAdapter = new SettingsAdapter();
        menuAdapter.setClickCallback(this);
        binding.recyclerviewMenu.setAdapter(menuAdapter);
    }

    private void prepareMenuData() {
        /*int[] icons = {R.drawable.ic_menu_patient_info_svg, R.drawable.ic_menu_physician_info_svg,
                R.drawable.ic_menu_sensor_svg, R.drawable.ic_menu_faq_svg,
                R.drawable.ic_menu_support_svg, R.drawable.ic_menu_change_password_svg,
                R.drawable.ic_menu_logout_svg};*/
        int[] icons = {R.drawable.ic_menu_patient_info_svg,
                R.drawable.ic_menu_logout_svg};
        String[] menuTitles = getResources().getStringArray(R.array.side_menu_items_list);

        menuItems = new ArrayList<>();
        SettingsItem menuItemHeader = new SettingsItem();
        menuItemHeader.setRowType(SettingsAdapter.ROW_TYPE_HEADER);
        menuItemHeader.setText("Test");
        menuItems.add(menuItemHeader);
        for (int menuPos = 0; menuPos < menuTitles.length; menuPos++) {
            SettingsItem menuItem = new SettingsItem();
            menuItem.setRowType(SettingsAdapter.ROW_TYPE_ITEM);
            menuItem.setIcon(icons[menuPos]);
            menuItem.setText(menuTitles[menuPos]);
            menuItems.add(menuItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuItems != null) {
            SettingsItem menuItemHeader = menuItems.get(0);
            menuItemHeader.setText("Test");
            menuAdapter.setData(menuItems);
        }
    }

    @Override
    public void onClickPosition(int position) {
        switch (position) {
            case MenuConstants.PROFILE:
                Intent userProfileIntent = new Intent(this, RegistrationActivity.class);
                startActivity(userProfileIntent);
                break;
            case MenuConstants.CHANGE_PWD:
                Intent changePasswordIntent = new Intent(this, ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;
            case MenuConstants.LOGOUT:
                showLogoutDialog();
                break;
            default:
                break;
        }
    }


    private void showLogoutDialog() {
        dialog = new AppAlertVerticalTwoButtonsDialog.AlertDialogBuilder(this, new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                dialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                onLogoutClick();
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_logout))
                .button1Text(getString(R.string.action_cancel))
                .button2Text(getString(R.string.action_logout))
                .build();
        dialog.showDialog();
        dialog.setButtonBlueUnselectBackground();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPresenter.disposeAll();
    }
}
