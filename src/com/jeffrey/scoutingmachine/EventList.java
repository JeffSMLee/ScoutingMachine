package com.jeffrey.scoutingmachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class EventList extends Activity {
	
	final static String URI = "http://www.thebluealliance.com/api/v1/events/list?year=2012";
	HttpClient client;
	EventListAdapter ad;
	JSONArray eventsArray;
	List<String> listHeaders;
	List<Event> starred;
	HashMap<String, List<Event>> listChildData;
	ExpandableListView eventList;
	SharedPreferences starredPrefs;
	Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);
		
		starredPrefs = getSharedPreferences("starred events", 0);
		editor = starredPrefs.edit();
				
		client = new DefaultHttpClient();
		
		eventList = (ExpandableListView) findViewById(R.id.lvEvents);
		
		eventList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(EventList.this, "hi", Toast.LENGTH_SHORT).show();
				Intent i = new Intent("com.jeffrey.scoutingmachine.MATCHLIST");
				i.putExtra("key", ((Event) ad.getChild(groupPosition, childPosition)).getKey());
				startActivity(i);
				return false;
			}
		});
		
		new GetEvents().execute();
	}
	
	public void starred(View v) {
		CheckBox star = (CheckBox) v;
		Event event = (Event) v.getTag();
		if (star.isChecked()) {
			starred.add(event);
			editor.putBoolean(event.getKey(), true);
			editor.apply();
			ad.notifyDataSetChanged();
		} else if (!star.isChecked()) {
			starred.remove(event);
			editor.putBoolean(event.getKey(), false);
			editor.apply();
			ad.notifyDataSetChanged();
		}
	}

	public class GetEvents extends AsyncTask<Void, Integer, Void> {

		public void populateEventsArray() throws ClientProtocolException, IOException, JSONException{
			HttpGet get = new HttpGet(URI);
			HttpResponse r = client.execute(get);
			int status = r.getStatusLine().getStatusCode();
			
			if (status < 300 && status > 199) {
				HttpEntity e = r.getEntity();
				String data = EntityUtils.toString(e);
				eventsArray = new JSONArray(data);
			}  else if (status < 600 && status >399) {
				cancel(true);
				Toast.makeText(EventList.this, "Error loading data", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				populateEventsArray();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			listHeaders = new ArrayList<String>();
			listChildData = new HashMap<String, List<Event>>();
			
			listHeaders.add("Starred");
			listHeaders.add("All");
			
			
			
			
			List<Event> all = new ArrayList<Event>();
			try {
				for (int i = 0; i < eventsArray.length(); i++) {
					all.add(new Event(eventsArray.getJSONObject(i).getString("name"), 
							eventsArray.getJSONObject(i).getString("start_date").replace("T00:00:00", ""), 
							eventsArray.getJSONObject(i).getString("key")));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			starred = new ArrayList<Event>();
			for (Event event : all) {
				if (starredPrefs.getBoolean(event.getKey(), false)) {
					starred.add(event);
				}
			}
			
			
	
			listChildData.put(listHeaders.get(0), starred);
			listChildData.put(listHeaders.get(1), all);
			
			ad = new EventListAdapter(EventList.this, listHeaders, listChildData, starredPrefs);
			eventList.setAdapter(ad);
		}
		
	}	
}
