package com.faseapp.faseapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import Utils.MyDebugClass;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);
        scannerView= new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();

    }

    @Override
    public void handleResult(Result result) {
        if(result.getText()!=null){

            onBackPressed();
            MyDebugClass.showToast(getApplicationContext(),result.getText());
        }
        else{

            MyDebugClass.showToast(getApplicationContext(),"Error in scanning please try again");
        }
    }
    @Override
    public void onBackPressed() {

        scannerView.stopCamera();
        super.onBackPressed();
    }
}
