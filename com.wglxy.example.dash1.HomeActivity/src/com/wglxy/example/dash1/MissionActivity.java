package com.wglxy.example.dash1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
    	startActivity(new Intent(getApplicationContext(), ScanQRActivity.class));
    }
}
