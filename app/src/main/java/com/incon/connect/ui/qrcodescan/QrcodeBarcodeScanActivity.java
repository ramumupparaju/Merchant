package com.incon.connect.ui.qrcodescan;

import android.Manifest;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.google.zxing.Result;
import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.databinding.ActivityQrcodeBarcodescanBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.PermissionUtils;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by PC on 9/27/2017.
 */
public class QrcodeBarcodeScanActivity extends BaseActivity implements QrCodeBarcodeContract.View {

    private static final String TAG = QrcodeBarcodeScanActivity.class.getSimpleName();
    private ActivityQrcodeBarcodescanBinding binding;
    private QrCodeBarcodePresenter qrCodeBarcodePresenter;
    private ZXingScannerView mScannerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode_barcodescan;
    }

    @Override
    protected void initializePresenter() {
        qrCodeBarcodePresenter = new QrCodeBarcodePresenter();
        qrCodeBarcodePresenter.setView(this);
        setBasePresenter(qrCodeBarcodePresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setQrcodebarcodescanactivity(this);
        mScannerView = (ZXingScannerView) findViewById(R.id.barcodescanner);
    }

    private void openCameraToScan() {
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
                                mScannerView.setResultHandler(resultHandler);
                                mScannerView.startCamera();
                                break;
                            case PermissionUtils.PERMISSION_DENIED:
                                Logger.v(TAG, "CAMERA :" + "denied");
                            case PermissionUtils.PERMISSION_DENIED_FOREVER:
                                Logger.v(TAG, "CAMERA :" + "denied forever");
                            default:
                                AppUtils.shortToast(QrcodeBarcodeScanActivity.this, getString(
                                        R.string.location_permission_msg));
                                finish();
                                break;
                        }
                    }
                });
    }

    private ZXingScannerView.ResultHandler resultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {
            String uuid = "0d4e7ea7-d35f-4233-be2a-6e01b65e2bb9"; //TODO have to remove
//        qrCodeBarcodePresenter.getUserScannedInfo(uuid);

            setResult(Activity.RESULT_OK);
            finish();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        openCameraToScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void navigateToQrCodeBarcodeScree(UserInfoResponse userInfoResponse) {
        if (userInfoResponse != null) {
//            onWarrentyStarts();
        }
    }

}
