package com.zenobase.mindlog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class Event {

	private static final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private final Date timestamp;
	private final long duration;
	private final String tag;
	private final int rating;

	public Event(String tag, long duration, int rating) {
		this.timestamp = new Date();
		this.tag = tag;
		this.duration = duration;
		this.rating = rating;
	}

	public long getDuration() {
		return duration;
	}

	public int getRating() {
		return rating;
	}

	public ObjectNode toJson() {
		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put("timestamp", ISO8601.format(timestamp));
		node.putArray("tag").add(tag);
		node.put("duration", duration);
		node.put("rating", rating);
		return node;
	}
}
