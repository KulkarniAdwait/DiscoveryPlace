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
		//set the QR scan prompt text
		t = (TextView) findViewById(R.id.QrScreenDisplay);
		String qrScreenDisplay = null;
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			qrScreenDisplay = bundle.getString(getString(R.string.parameter_qrScreenDisplay));
		}
		// did not use String.isEmpty() because that is only available from
		// API 9 onwards and not in the API 8 that we have set as the current minimum
		// target to run on version 2.3.3
		if (qrScreenDisplay != null && !qrScreenDisplay.equals("")) {
			t.setText(qrScreenDisplay);
		} else {
			t.setText("Scan QR code");
		}

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
		if (resultCode == RESULT_OK) {

			String contents = intent.getStringExtra("SCAN_RESULT");
			// check if the file for the exhibit is in the system
			int fileId = getResources().getIdentifier(
					contents.toLowerCase(Locale.US), "xml", this.getPackageName());
			// if found then call the options list activity
			if (fileId > 0) {
				Bundle bundle = new Bundle();
				// converting to lower case because Android allows filenames,
				// and hence exhibit names, in lower case only
				bundle.putString(getString(R.string.parameter_exhibitName),	contents.toLowerCase(Locale.US));
				Intent optionsIntent = new Intent(getApplicationContext(), ScannedOptions.class);
				optionsIntent.putExtras(bundle);
				startActivity(optionsIntent);
			}
			// else show an error and go back to scan prompt
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("You seem to have scanned a code I haven't seen before. Please contact an administrator.");
				builder.setTitle("Oops!");
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		} else if (resultCode == RESULT_CANCELED) {
			// Handle cancel
		}
	}
}
