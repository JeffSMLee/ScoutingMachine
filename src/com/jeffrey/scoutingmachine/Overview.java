package com.jeffrey.scoutingmachine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;

public class Overview extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);
		
		
	}

	public void startScouting(View view) {
		//startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		
		Intent i = new Intent("com.jeffrey.scoutingmachine.EVENTLIST");
		startActivity(i);			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}	
}
