package org.vatvit.irccloud.events;

import org.json.JSONObject;

public interface EventListener {
	public void onEvent(JSONObject event);
}
