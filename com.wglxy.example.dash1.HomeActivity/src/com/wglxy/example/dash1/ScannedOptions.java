package com.wglxy.example.dash1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ScannedOptions extends Activity {

	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();

	// DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	ArrayAdapter<String> adapter;

	ListView optionsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanned_options);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		// setListAdapter(adapter);
		optionsList = (ListView) findViewById(R.id.optionsList);
		optionsList.setAdapter(adapter);
		getOptions();
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

	public void onClickQuizOption(View v) {
		startActivity(new Intent(getApplicationContext(), QuizActivity.class));
	}

	public void onClickOption(View v) {

	}

	private void getOptions() {
		if (this.getIntent().getExtras() != null) {
			Bundle bundle = this.getIntent().getExtras();// get the intent &
															// bundle passed by
															// Caller
			String fileName = bundle.getString("exhibitName");
			try {
				int fileId = getResources().getIdentifier(fileName, "xml", this.getPackageName());

				LinkedList<Option> options = parseOptions(fileId);
				for(int i=0;i<options.size();i++)
				{
					addItems(options.get(i).type, " ");
				}
			} catch (IOException e) {
				e.printStackTrace();

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
		}
	}

	public LinkedList<Option> parseOptions(int inXml)
			throws XmlPullParserException, IOException {

		XmlResourceParser parser = getBaseContext().getResources().getXml(inXml);

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
