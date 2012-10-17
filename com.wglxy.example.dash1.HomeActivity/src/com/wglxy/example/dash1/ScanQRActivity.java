package com.wglxy.example.dash1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ScanQRActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_scan_qr, menu);
        return true;
    }
    
    public void onClickScanCode(View v) {
    	startActivity(new Intent(getApplicationContext(), ScannedOptions.class));    	
    }
}
