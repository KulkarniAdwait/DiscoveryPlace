package com.wglxy.example.dash1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParserException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.VideoView;

public class ScannedOptions extends Activity {

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	// DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	ArrayAdapter<String> adapter;
	VideoView videoView;
	ListView optionsList;
	LinkedList<Option> options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanned_options);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		optionsList = (ListView) findViewById(R.id.optionsList);
		optionsList.setAdapter(adapter);
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

	public void onClickQuizOption(View v) {
		startActivity(new Intent(getApplicationContext(), QuizActivity.class));
	}

	public void onClickOption(View v) {

	}

	private void getOptions() {
		if (this.getIntent().getExtras() != null) {
			// get the intent & bundle passed by calling activity
			Bundle bundle = this.getIntent().getExtras();
			String fileName = null;
			if (bundle != null) {
				fileName = bundle.getString("exhibitName");
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
		listItems.add(display);
		adapter.notifyDataSetChanged();
	}

}
