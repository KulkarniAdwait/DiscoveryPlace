package com.wglxy.example.dash1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.xmlpull.v1.XmlPullParserException;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.VideoView;

public class ScannedOptions extends HomeActivity {

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	// DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	ArrayAdapter<String> adapter;
	VideoView videoView;
	ListView optionsList;
	LinkedList<Option> options;
	String mode = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanned_options);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		optionsList = (ListView) findViewById(R.id.optionsList);
		optionsList.setAdapter(adapter);
		if (this.getIntent().getExtras() != null) {
			// get the intent & bundle passed by calling activity
			Bundle bundle = this.getIntent().getExtras();
			if (bundle != null) {
				mode = bundle.getString(getString(R.string.parameter_mode));
			}
		}
		getOptions();

		optionsList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				clickHandler(position);
			}
		});
	}

	public void clickHandler(int position) {
		if (options.get(position).type.equals("video")) {
			// get uri of video file from the name in the xml
			Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + options.get(position).details);
			Bundle bundle = new Bundle();
			bundle.putString("uri", uri.toString());
			Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	private void getOptions() {
		if (this.getIntent().getExtras() != null) {
			// get the intent & bundle passed by calling activity
			Bundle bundle = this.getIntent().getExtras();
			String fileName = null;
			if (bundle != null) {
				fileName = bundle.getString(getString(R.string.parameter_exhibitName));
			}
			// do only if the parameter has been passed by calling activity.
			if (fileName != null && !fileName.equals("")) {
				try {
					int fileId = getResources().getIdentifier(fileName, "xml",
							this.getPackageName());

					options = parseOptions(fileId);
					for (int i = 0; i < options.size(); i++) {
						addItems(options.get(i).type, " ");
					}
				} catch (IOException e) {
					e.printStackTrace();

				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public LinkedList<Option> parseOptions(int inXml)
			throws XmlPullParserException, IOException {

		XmlResourceParser parser = getBaseContext().getResources()
				.getXml(inXml);

		Option option = null;
		LinkedList<Option> options = new LinkedList<Option>();
		int event = 0;
		try {
			parser.next();
			event = parser.getEventType();
		} catch (Exception e) {

		}
		while (event != XmlResourceParser.END_DOCUMENT) {
			switch (event) {
			case XmlResourceParser.START_TAG:
				if (parser.getName().equals("option")) {
					option = new Option();

				} else if (parser.getName().equals("type")) {
					option.type = parser.nextText();
				} else if (parser.getName().equals("details")) {
					option.details = parser.nextText();
				}
				break;
			case XmlResourceParser.END_TAG:

				if (parser.getName().equals("option")) {
					options.add(option);
				}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return options;
	}

	public class Option {
		String type;
		String details;

		public Option() {

		}
	}

	// METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	public void addItems(String display, String value) {
		if(display.equals(getString(R.string.option_text))) {
			listItems.add(getString(R.string.option_text_display));
		}
		else if (display.equals(getString(R.string.option_game))) {
			listItems.add(getString(R.string.option_game_display));
		}
		else if(display.equals(getString(R.string.option_quiz))) {
			listItems.add(getString(R.string.option_quiz_display));
		}
		else if(display.equals(getString(R.string.option_video))) {
			listItems.add(getString(R.string.option_video_display));
		}
		adapter.notifyDataSetChanged();
	}
	
	public void onClickQuizOption(View v) {
		startActivity(new Intent(getApplicationContext(), QuizActivity.class));
	}

	public void onClickOption(View v) {

	}
	
	public void nextExhibit(View v){
		Bundle bundle = null;
		Intent intent = null;
		//using if else instead of switch to continue using older compiler
		if(mode.equals(getString(R.string.mode_freeRoam))) {
			bundle = new Bundle();
			bundle.putString(getString(R.string.parameter_qrScreenDisplay), getString(R.string.freeRoam_intro));
			bundle.putString(getString(R.string.parameter_mode), this.mode);
			intent = new Intent(getApplicationContext(), ScanQRActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else if(mode.equals(getString(R.string.mode_mission))) {
			bundle = new Bundle();
			bundle.putString(getString(R.string.parameter_missionActivityFlag), getString(R.string.parameter_next));
			intent = new Intent(getApplicationContext(), MissionActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}
}
