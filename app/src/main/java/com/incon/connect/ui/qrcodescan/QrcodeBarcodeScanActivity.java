package com.incon.connect.ui.qrcodescan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.zxing.ResultPoint;
import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.databinding.ActivityQrcodeBarcodescanBinding;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.utils.Logger;
import com.incon.connect.utils.PermissionUtils;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 9/27/2017.
 */
public class QrcodeBarcodeScanActivity extends BaseActivity implements QrCodeBarcodeContract.View {

    private static final String TAG = QrcodeBarcodeScanActivity.class.getSimpleName();
    private ActivityQrcodeBarcodescanBinding binding;
    private CompoundBarcodeView qrCodeScanView;
    private ProgressBar progressBar;
    private View reScanImageView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode_barcodescan;
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setQrcodeScanActivity(this);
        qrCodeScanView = (CompoundBarcodeView) findViewById(R.id.qrcode_scanner);
    }

    private void startQRScan() {

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

                                if (qrCodeScanView != null) {
                                    qrCodeScanView.decodeContinuous(qrcodeCallback);
                                    resumeQRScan();
                                    qrCodeScanView.decodeContinuous(qrcodeCallback);
                                    //  startQRDisplayTimer();
                                }
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
    private void reStartQRScan() {
        if (qrCodeScanView != null) {
            qrCodeScanView.decodeContinuous(qrcodeCallback);
            qrCodeScanView.getViewFinder().setVisibility(View.VISIBLE);
            reScanImageView.setVisibility(View.GONE);
           // startQRDisplayTimer();
            resumeQRScan();
        }
    }

    private void resumeQRScan() {

        startQRScan();

    }

    public BarcodeCallback qrcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String output = result.getText();
            if (!TextUtils.isEmpty(output)) {
                Log.d(TAG, "qrscan result: " + output);
                if (output.contains("~")) {
                    stopQRScan(false);
                    progressBar.setVisibility(View.VISIBLE);
                    String[] data = output.split("~");
                    //setGenieMacAddress(data[1]);
//                    startMqttReceiver(true);
                   // subscirbeTo(getQrScanRecievingTopic());
                   // sendMacAddressToGenie();
                }
                else {
                    //invalid output
                    progressBar.setVisibility(View.GONE);
                    stopQRScan(false);
                   // showInvalidOutputMessage();
                }
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    private void stopQRScan(boolean b) {

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void navigateToPreviousScreen(String qrCode) {
        Intent intentData = new Intent();
        intentData.putExtra(IntentConstants.SCANNED_CODE, qrCode);
        setResult(Activity.RESULT_OK, intentData);
        finish();
    }
}
