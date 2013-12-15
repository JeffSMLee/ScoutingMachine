package com.jeffrey.scoutingmachine;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class EventListAdapter extends BaseExpandableListAdapter {

	private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child event
    private HashMap<String, List<Event>> listChildData;
    SharedPreferences starredPrefs;
    static final Event FAIL = new Event(null, null, null);
 
    public EventListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<Event>> listChildData, SharedPreferences starredPreferences) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listChildData = listChildData;
        this.starredPrefs = starredPreferences;
    }
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if (listChildData.get(listDataHeader.get(groupPosition)).size() > 0) {
			return listChildData.get(listDataHeader.get(groupPosition)).get(childPosition);			
		} else {
			return FAIL;
		}
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (getChild(groupPosition, childPosition) != FAIL) {
			final Event event = (Event) getChild(groupPosition, childPosition);
			 
	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) this.context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.event_list_item, null);
	        }
	 
	        TextView name = (TextView) convertView
	                .findViewById(R.id.tvEventName);
	        name.setText(event.getName());
	        
	        TextView startDate = (TextView) convertView
	                .findViewById(R.id.tvStartDate);
	        startDate.setText(event.getStartDate());
	        
	        final CheckBox star = (CheckBox) convertView.findViewById(R.id.checkStar);
	        if (starredPrefs.getBoolean(event.getKey(), false)) {
	        	star.setChecked(true);
	        } else {
	        	star.setChecked(false);
	        }
	        
	        star.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					star.setTag(event);
				}
			});
	        
	        
	        return convertView;
		} else {
			return null;
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return listChildData.get(this.listDataHeader.get(groupPosition))
                .size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list_group, null);
        }
 
        TextView header = (TextView) convertView.findViewById(R.id.tvGroupHeader);
        header.setText(headerTitle);
 
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}