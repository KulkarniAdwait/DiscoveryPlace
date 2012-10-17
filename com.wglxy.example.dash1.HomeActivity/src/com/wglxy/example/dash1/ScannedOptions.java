package com.wglxy.example.dash1;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ScannedOptions extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_scanned_options, menu);
        return true;
    }
    
    public void onClickVideoOption(View v) {
		String url = "http://www.youtube.com/watch?v=SE3vCm_4wCI";
		String autoPlay = "&autoplay=1";
		
		Uri uri = Uri.parse(url + autoPlay);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    
    public void onClickQuizOption(View v){
    	startActivity(new Intent(getApplicationContext(), QuizActivity.class));
    }
    
    public void onClickOption(View v) {
    	
    }
}
