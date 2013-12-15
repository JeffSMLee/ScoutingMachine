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
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class MatchList extends Activity {
	HttpClient client;
	final static String BASE_URI = "http://www.thebluealliance.com/api/v1/event/details?event=";
	JSONArray matchArray;
	String eventKey;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.match_list);
		
		eventKey = getIntent().getStringExtra("key");
		
		client = new DefaultHttpClient();
	}
	
	public class GetEvents extends AsyncTask<Void, Integer, Void> {

		public void populateMatchArray() throws ClientProtocolException, IOException, JSONException{
			String URI = BASE_URI + eventKey;
			HttpGet get = new HttpGet(URI);
			HttpResponse r = client.execute(get);
			int status = r.getStatusLine().getStatusCode();
			
			if (status < 300 && status > 199) {
				HttpEntity e = r.getEntity();
				String data = EntityUtils.toString(e);
				matchArray = new JSONArray(data);
			}
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				populateMatchArray();
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
			
		}
		
	}	

}
