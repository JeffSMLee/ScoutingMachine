package com.jeffrey.scoutingmachine;

public class Event {
	private String name, startDate, key;

	public Event(String name, String startDate, String key) {
		this.name = name;
		this.startDate = startDate;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
