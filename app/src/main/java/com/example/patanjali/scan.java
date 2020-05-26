package com.example.patanjali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class scan extends AppCompatActivity {

    Boolean done;
    String data1;
    AsyncLogin al;
    private IntentIntegrator qrScan;
    public static final List<String> ONE_DIMENSIONAL_FORMATS = Collections.unmodifiableList(
            Arrays.asList(BarcodeFormat.EAN_13.toString()));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        done = false;
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan a barcode");
        qrScan.setCameraId(0);  // Use a specific camera of the device
        qrScan.setOrientationLocked(true);
        qrScan.setBeepEnabled(true);
        qrScan.setCaptureActivity(CaptureActivityPortrait.class);
        qrScan.initiateScan();
        al = new AsyncLogin();
        al.execute();
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        //  ProgressDialog pdLoading = new ProgressDialog(verify_mobile.this);
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(String... params) {

            qrScan.initiateScan(ONE_DIMENSIONAL_FORMATS);
            while (!done);
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }
    public void call(String s)
    {
        if(s!=null)
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    data1 = "";
                } else {
                    try {
                        data1 = result.getContents();
                        done = true;
                        //Toast.makeText(getApplicationContext(),data1+"in scan",Toast.LENGTH_LONG).show();
                        try {
//                            FrameLayout f1 = (FrameLayout) findViewById(R.id.fl1);
//                            f1.removeAllViews();
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("barcode", data1);

                            startActivity(intent);
                        }
                        catch ( Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), result.getContents() + e, Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
        }
    }
}
