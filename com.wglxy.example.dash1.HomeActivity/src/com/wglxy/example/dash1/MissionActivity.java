package com.wglxy.example.dash1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MissionActivity extends Activity {
	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mission, menu);
        return true;
    }
    
    public void onClickMission(View v) {
    	//@Gloria: set the text you want displayed in the scan qr screen here
    	String qrScreenDisplay = "insert the QR scan prompt text here!";
    	Bundle bundle = new Bundle();
		bundle.putString(getString(R.string.parameter_qrScreenDisplay), qrScreenDisplay);
		Intent scanQrIntent = new Intent(getApplicationContext(),
				ScanQRActivity.class);
		scanQrIntent.putExtras(bundle);
		startActivity(scanQrIntent);
    }
}
