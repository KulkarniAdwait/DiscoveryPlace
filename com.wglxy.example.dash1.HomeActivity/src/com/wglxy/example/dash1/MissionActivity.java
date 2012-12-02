package com.wglxy.example.dash1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.xmlpull.v1.XmlPullParserException;
import com.wglxy.example.dash1.CurrentMissionHandler.Exhibit;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MissionActivity extends HomeActivity {
	// LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	// DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	ArrayAdapter<String> adapter;
	ListView missionsList;
	LinkedList<Mission> missions;
	CurrentMissionHandler missionHandler = null;
	String currentMission = null;
	int currentIndex = 0;
	  
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mission);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle nextBundle = this.getIntent().getExtras();
        if(nextBundle != null) {
        	bundleHandler(nextBundle);        	
        }
    }
    
    public void clickHandler(int position) {
		if (!missions.get(position).details.equals("")) {
			currentMission = missions.get(position).details;
			missionHandler = new CurrentMissionHandler(currentMission, CurrentMissionHandler.EMPTY_INDEX, getApplicationContext());
			openQrScannerActivity();
		}
	}    
   
    private void openQrScannerActivity() {

		Exhibit currentExhibit = missionHandler.GetNextExhibit();
		
		if(currentExhibit == missionHandler.EXHIBIT_MISSION_END) {
			Intent missionCompleteIntent = new Intent(getApplicationContext(), MissionCompleteActivity.class);
			startActivity(missionCompleteIntent);
		}
		else if(currentExhibit != null) {
	    	String qrScreenDisplay = currentExhibit.getType();
	    	Bundle bundle = new Bundle();
			bundle.putString(getString(R.string.parameter_qrScreenDisplay), qrScreenDisplay);
			bundle.putString(getString(R.string.parameter_qrScreenCurrentExhibit), currentExhibit.getDetails());
			bundle.putString(getString(R.string.parameter_mode), getString(R.string.mode_mission));
			Intent scanQrIntent = new Intent(getApplicationContext(),
					ScanQRActivity.class);
			scanQrIntent.putExtras(bundle);
			startActivity(scanQrIntent);
		}
    }
    
    private void getMissions() {
		String fileName = "missions";
			try {
				int fileId = getResources().getIdentifier(fileName, "xml",
						this.getPackageName());

				missions = parseMissions(fileId);
				for (int i = 0; i < missions.size(); i++) {
					addItems(missions.get(i).name, missions.get(i).details);
				}
			} catch (IOException e) {
				e.printStackTrace();

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
	}

	public LinkedList<Mission> parseMissions(int inXml)
			throws XmlPullParserException, IOException {

		XmlResourceParser parser = getBaseContext().getResources()
				.getXml(inXml);

		Mission mission = null;
		LinkedList<Mission> missions = new LinkedList<Mission>();
		int event = 0;
		try {
			parser.next();
			event = parser.getEventType();
		} catch (Exception e) {

		}
		while (event != XmlResourceParser.END_DOCUMENT) {
			switch (event) {
			case XmlResourceParser.START_TAG:
				if (parser.getName().equals("mission")) {
					mission = new Mission();

				} else if (parser.getName().equals("name")) {
					mission.name = parser.nextText();
				} else if (parser.getName().equals("details")) {
					mission.details = parser.nextText();
				}
				break;
			case XmlResourceParser.END_TAG:

				if (parser.getName().equals("mission")) {
					missions.add(mission);
				}
				break;
			default:
				break;
			}
			event = parser.next();
		}
		return missions;
	}

	public class Mission {
		String name;
		String details;

		public Mission() {

		}
	}

	// METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	public void addItems(String display, String value) {
		listItems.add(display);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onPause() {
        super.onPause();
        if(missionHandler != null) {
        	missionHandler.Save();
        }
	}
	
	protected void onResume(Bundle bundle) {
        super.onResume();
        bundleHandler(bundle);
    }
	
	private void bundleHandler(Bundle bundle) {
		String flag = null;
        if (bundle != null) {
			flag = bundle.getString(getString(R.string.parameter_missionActivityFlag));
		}
        if(flag != null && flag.equals(getString(R.string.parameter_next)))
        {
            if(missionHandler == null) {
            	missionHandler = CurrentMissionHandler.Get(getApplicationContext());
            }
            openQrScannerActivity();
        }
        else if(flag!= null && flag.equals(getString(R.string.parameter_list))) {
        	setContentView(R.layout.activity_mission);
            
            adapter = new ArrayAdapter<String>(this,
    				android.R.layout.simple_list_item_1, listItems);
            missionsList = (ListView) findViewById(R.id.missionsList);
            missionsList.setAdapter(adapter);
            getMissions();
            
    		missionsList.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View view,
    					int position, long id) {
    				clickHandler(position);
    			}
    		});
        }
	}
}
