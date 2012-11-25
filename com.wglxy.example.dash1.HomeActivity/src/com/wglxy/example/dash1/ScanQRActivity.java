package com.wglxy.example.dash1;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScanQRActivity extends Activity {
	TextView t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        t = new TextView(this);
        t = (TextView)findViewById(R.id.QrScreenDisplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_scan_qr, menu);
        return true;
    }
    
    public void onClickScanCode(View v) {
    	IntentIntegrator integrator = new IntentIntegrator(this);
    	integrator.initiateScan();    	
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                Bundle bundle = new Bundle();
                bundle.putString("exhibitName", contents.toLowerCase(Locale.US));
                Intent optionsIntent = new Intent(getApplicationContext(), ScannedOptions.class);
                optionsIntent.putExtras(bundle);
                startActivity(optionsIntent);
                
                //String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        //}
    }
}
