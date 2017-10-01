package com.incon.connect.ui.qrcodescan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;
import com.incon.connect.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.connect.ui.BaseActivity;
import com.incon.connect.ui.warrantyregistration.WarrantyRegistrationActivity;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by PC on 9/27/2017.
 */
public class QrcodeBarcodeScanActivity extends BaseActivity implements ZXingScannerView.
        ResultHandler , QrCodeBarcodeContract.View {
    private ZXingScannerView mScannerView;
    private static final int PERMISSIONS_REQUEST_CAPTURE_IMAGE = 1;
    private QrCodeBarcodePresenter qrCodeBarcodePresenter;

    @Override
    protected int getLayoutId() {
        return 0;
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
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch
                (requestCode) {
            case PERMISSIONS_REQUEST_CAPTURE_IMAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("", "permission granted success");

                } else {
                    Log.d("", "permission denied");
                }
                return;
            }

            default:
        }

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

    public  void onWarrentyStarts() {
        Intent intent = new Intent(QrcodeBarcodeScanActivity.this ,
                WarrantyRegistrationActivity.class);
        startActivity(intent);


    }
    @Override
    public void navigateToQrCodeBarcodeScree(UserInfoResponse userInfoResponse) {
        if (userInfoResponse != null) {
            onWarrentyStarts();
        }
    }
}
