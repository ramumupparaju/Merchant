package com.incon.connect.ui.qrcodescan;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.zxing.Result;
import com.incon.connect.ui.BaseActivity;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by PC on 9/27/2017.
 */
public class QrcodeBarcodeScanActivity extends BaseActivity implements ZXingScannerView.
        ResultHandler {
    private ZXingScannerView mScannerView;
    private static final int PERMISSIONS_REQUEST_CAPTURE_IMAGE = 1;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initializePresenter() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        mScannerView.resumeCameraPreview(this);

    }
}
