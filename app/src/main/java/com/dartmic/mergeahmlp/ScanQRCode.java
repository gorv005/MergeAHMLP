package com.dartmic.mergeahmlp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;

public class ScanQRCode extends Activity implements ZXingScannerView.ResultHandler {

    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    private List<BarcodeFormat> formats = new ArrayList<>();


    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.scan_qr);
        mScannerView = new ZXingScannerView(this);
        formats.add(BarcodeFormat.QR_CODE);
    }


    @Override
    public void onResume() {
        super.onResume();
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.setAutoFocus(true);
        mScannerView.setFormats(formats);
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        mScannerView.stopCamera();
        super.onPause();
    }

    @Override
    public void handleResult(Result rawResult) {

        Log.e(TAG, rawResult.getText());
        Log.e(TAG, rawResult.getBarcodeFormat().toString());

        Intent intent = new Intent(ScanQRCode.this, QRCodePoints.class);
        intent.putExtra("qr", rawResult.getText());

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
