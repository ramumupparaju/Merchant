package com.incon.connect.ui.qrcodescan;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Camera;
import android.os.Bundle;

import com.google.zxing.Result;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.databinding.ActivityQrcodeBarcodescanBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistrationActivity;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.PermissionUtils;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by PC on 9/27/2017.
 */
public class QrcodeBarcodeScanActivity extends BaseActivity implements ZXingScannerView.
        ResultHandler , QrCodeBarcodeContract.View  {
    private ZXingScannerView mScannerView;
    Camera camera;
    private QrCodeBarcodePresenter qrCodeBarcodePresenter;
    private ActivityQrcodeBarcodescanBinding binding;
    private static final String TAG = QrcodeBarcodeScanActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return  R.layout.activity_qrcode_barcodescan;
    }

    @Override
    protected void initializePresenter() {
       qrCodeBarcodePresenter = new QrCodeBarcodePresenter();
        qrCodeBarcodePresenter.setView(this);
        setBasePresenter(qrCodeBarcodePresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setQrcodebarcodescanactivity(this);
       mScannerView = (ZXingScannerView) findViewById(R.id.barcodescanner);
        openCameraToUpload();
    }

    private void openCameraToUpload() {

        PermissionUtils.getInstance().grantPermission(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                new PermissionUtils.Callback() {
                    @Override
                    public void onFinish(HashMap<String, Integer> permissionsStatusMap) {
                        int storageStatus = permissionsStatusMap.get(
                                Manifest.permission.CAMERA);
                        switch (storageStatus) {
                            case PermissionUtils.PERMISSION_GRANTED:
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


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
      /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
        String uuid = "0d4e7ea7-d35f-4233-be2a-6e01b65e2bb9";
        qrCodeBarcodePresenter.getUserScannedInfo(uuid);
//        finish();
//        mScannerView.stopCamera();
         mScannerView.resumeCameraPreview(this);

    }


    @Override
    public void navigateToQrCodeBarcodeScree(UserInfoResponse userInfoResponse) {
        if (userInfoResponse != null) {
            onWarrentyStarts();
        }
    }

    private void onWarrentyStarts() {
        Intent intent = new Intent(QrcodeBarcodeScanActivity.this ,
                WarrantyRegistrationActivity.class);
        startActivity(intent);
    }
}
