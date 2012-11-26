package com.wglxy.example.dash1;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
		t = (TextView) findViewById(R.id.QrScreenDisplay);
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
		// if (requestCode == 0) {
		if (resultCode == RESULT_OK) {

			String contents = intent.getStringExtra("SCAN_RESULT");
			// check if the file for the exhibit is in the system
			int fileId = getResources().getIdentifier(contents.toLowerCase(Locale.US), "xml",
					this.getPackageName());
			//if found then call the options list activity
			if (fileId > 0) {
				Bundle bundle = new Bundle();
				bundle.putString("exhibitName", contents.toLowerCase(Locale.US));
				Intent optionsIntent = new Intent(getApplicationContext(),
						ScannedOptions.class);
				optionsIntent.putExtras(bundle);
				startActivity(optionsIntent);
			}
			//else show an error and go back to scan prompt
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("You seem to have scanned a code I haven't seen before. Please contact the administrator.");
				builder.setTitle("Oops!");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
				
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
			

			// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
			// Handle successful scan
		} else if (resultCode == RESULT_CANCELED) {
			// Handle cancel
		}
		// }
	}
}
