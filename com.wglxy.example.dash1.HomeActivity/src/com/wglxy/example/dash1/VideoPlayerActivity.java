package com.wglxy.example.dash1;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.widget.VideoView;
import android.widget.MediaController;

public class VideoPlayerActivity extends Activity {

	VideoView mVideoView;
	MediaController mediaController;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Bundle bundle = this.getIntent().getExtras();
		String uriString = null;
		if (bundle != null) {
			uriString = bundle.getString("uri");
		}
		
		Uri uri = Uri.parse(uriString);
		
		mVideoView = new VideoView(this);
		mVideoView.setVideoURI(uri);
		//go back to previous activity once video finishes playing
		mVideoView.setOnCompletionListener(new OnCompletionListener () {
				public void onCompletion(MediaPlayer v) {
					finish();
				}
		});
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();
		mVideoView.start();
		setContentView(mVideoView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_video_player, menu);
		return true;
	}

}
