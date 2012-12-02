package com.wglxy.example.dash1;

import java.util.Locale;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScanQRActivity extends HomeActivity {
	TextView t;
	// this is only used in mission mode to check if the scanned QR is the QR of
	// the prompted item
	String currentExhibit = null;
	String mode = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// set the QR scan prompt text
		t = (TextView) findViewById(R.id.QrScreenDisplay);
		String qrScreenDisplay = null;
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			qrScreenDisplay = bundle
					.getString(getString(R.string.parameter_qrScreenDisplay));

			currentExhibit = bundle
					.getString(getString(R.string.parameter_qrScreenCurrentExhibit));
			
			mode = bundle.getString(getString(R.string.parameter_mode));
		}
		// did not use String.isEmpty() because that is only available from
		// API 9 onwards and not in the API 8 that we have set as the current
		// minimum
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
			//if the scanned item is not the prompted item in mission mode
			if (this.mode.equals(getString(R.string.mode_mission)) && currentExhibit != null && !currentExhibit.equalsIgnoreCase(contents)) {
				showAlertDialog("Incorrect!", "You seem to have scanned an incorrect code. Try again.");
				return;
			}
			// check if the file for the exhibit is in the system
			int fileId = getResources().getIdentifier(
					contents.toLowerCase(Locale.US), "xml",
					this.getPackageName());
			// if found then call the options list activity
			if (fileId > 0) {
				Bundle bundle = new Bundle();
				// converting to lower case because Android allows
				// filenames,
				// and hence exhibit names, in lower case only
				bundle.putString(getString(R.string.parameter_exhibitName),
						contents.toLowerCase(Locale.US));
				bundle.putString(getString(R.string.parameter_mode), this.mode);
				Intent optionsIntent = new Intent(getApplicationContext(),
						ScannedOptions.class);
				optionsIntent.putExtras(bundle);
				startActivity(optionsIntent);
			}
			// else show an error and go back to scan prompt
			else {
				showAlertDialog("Oops!", "You seem to have scanned a code I haven't seen before. Please contact an administrator.");
			}
			
		} else if (resultCode == RESULT_CANCELED) {
			// Handle cancel
			// not required as of now
		}
	}
	
	private void showAlertDialog(String title, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(text);
		builder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {

					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onBackPressed() {
		Bundle bundle = new Bundle();
		bundle.putString(getString(R.string.parameter_missionActivityFlag),
				getString(R.string.parameter_list));
		bundle.putString(getString(R.string.parameter_mode), this.mode);
		Intent intent = new Intent(getApplicationContext(),
				MissionActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
